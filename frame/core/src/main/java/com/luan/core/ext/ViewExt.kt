package com.luan.core.ext

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.lifecycle.LifecycleOwner
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.ToastUtils
import com.luan.core.R
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * @package    com.luan.core.ext
 * @author     luan
 * @date       2020/9/16
 * @des
 */

const val clickInterval = 500//点击间隔

fun Context.inflateView(@LayoutRes layoutId: Int): View {
    return LayoutInflater.from(this).inflate(layoutId, null, false)
}

//切换密码显示与隐藏
fun EditText.togglePasswordVisible(isShow: Boolean?=null){
    if (isShow!=null){
        transformationMethod = if (!isShow)
            HideReturnsTransformationMethod.getInstance()
        else
            PasswordTransformationMethod.getInstance()
    }else{
        transformationMethod = if (transformationMethod == PasswordTransformationMethod.getInstance())
            HideReturnsTransformationMethod.getInstance()
        else
            PasswordTransformationMethod.getInstance()
    }

    setSelection(text.toString().length-1)
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

//批量设置事件,并且防止暴力点击
fun <T : View> Array<T>.addClicks(onClickListener: View.OnClickListener) {
    forEach {
        it.setOnClickListener {
            val lastClick = it.getTag(R.id.core_fast_click_id) as? Long
            val currentTimeMillis = System.currentTimeMillis()
            if (lastClick == null || currentTimeMillis - lastClick > clickInterval) {
                it.setTag(R.id.core_fast_click_id, currentTimeMillis)
                onClickListener.onClick(it)
            }
        }
    }
}

//单个点击事件
fun <T : View> T.addClick(onClickListener: (view: View) -> Unit) {
    setOnClickListener {
        val lastClick = it.getTag(R.id.core_fast_click_id) as? Long
        val currentTimeMillis = System.currentTimeMillis()
        if (lastClick == null || currentTimeMillis - lastClick > clickInterval) {
            it.setTag(R.id.core_fast_click_id, currentTimeMillis)
            onClickListener.invoke(it)
        }
    }
}

fun EditText.isEmpty() = text.toString().trim().isNullOrEmpty()
fun TextView.toTextString() = text.toString()

//表单验证
fun Map<String, () -> Boolean>.fromCheck(block: () -> Unit) {
    forEach {
        if (it.value.invoke()) {
            it.key.toast()
            return
        }
        if (keys.last() == it.key) {
            block.invoke()
        }
    }
}

fun Map<String, Boolean>.fromCheckBool(block: () -> Unit) {
    forEach {
        if (it.value) {
            it.key.toast()
            return
        }
        if (keys.last() == it.key) {
            block.invoke()
        }
    }
}

fun View.isShow(isShow: Boolean) {
    visibility = if (isShow) View.VISIBLE else View.GONE
}

//输入文字字数限制联动
fun EditText.syncMaxLengthByTextView(textView: TextView) {
    val lengthFilter = filters?.find { it is InputFilter.LengthFilter } as? InputFilter.LengthFilter
    val max = lengthFilter?.max
    max?.let { textView.text = "0/$it" }
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            max?.let {
                textView.text = "${s.length}/$it"
            }
        }
    })
}
