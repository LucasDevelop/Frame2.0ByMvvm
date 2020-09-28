package com.luan.core.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import com.luan.core.R
import com.luan.core.exception.CoreInitException
import com.luan.core.ext.*
import java.util.*

/**
 * @package    com.luan.core.ui.widget
 * @author     luan
 * @date       2020/9/16
 * @des        根据状态切换布局
 */
class StatusView : FrameLayout {

    enum class Status(@LayoutRes val layoutId: Int?) {
        NUL(null),
        MASTER(null),
        LOADING(R.layout.base_view_switch_loading),
        EMPTY(R.layout.base_view_switch_empty),
        NET_ERROR(R.layout.base_view_switch_net_error),
        NOT_NETWORK(R.layout.base_view_switch_not_network),
        SERVER_ERROR(R.layout.base_view_switch_server_error),
        TIMEOUT(R.layout.base_view_switch_timeout)
    }

    constructor(context: Context) : super(context) {
        initView(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(context, attrs)
    }

    var currentStatus = Status.NUL
    val statusViewCache = TreeMap<Status, View>()

    private fun initView(context: Context, attrs: AttributeSet?) {
        //该控件只能有一个子view
        if (childCount > 1) {
            throw CoreInitException("StatusView控件只能有一个子view")
        } else if (childCount == 1) {//从布局中获取master view
            statusViewCache[Status.MASTER] = getChildAt(0)
            changeStatus(Status.MASTER)
        }
        //初始化布局
        statusViewCache[Status.LOADING] =
            context.inflateView(requireNotNull(Status.LOADING.layoutId))
                .apply { setNewLayoutParams(LParam.MM) }
        statusViewCache[Status.EMPTY] =
            context.inflateView(requireNotNull(Status.EMPTY.layoutId))
            .apply { setNewLayoutParams(LParam.MM) }
        statusViewCache[Status.NET_ERROR] =
            context.inflateView(requireNotNull(Status.NET_ERROR.layoutId))
                .apply { setNewLayoutParams(LParam.MM) }
        statusViewCache[Status.NOT_NETWORK] =
            context.inflateView(requireNotNull(Status.NOT_NETWORK.layoutId))
                .apply { setNewLayoutParams(LParam.MM) }
        statusViewCache[Status.SERVER_ERROR] =
            context.inflateView(requireNotNull(Status.SERVER_ERROR.layoutId))
                .apply { setNewLayoutParams(LParam.MM) }
        statusViewCache[Status.TIMEOUT] =
            context.inflateView(requireNotNull(Status.TIMEOUT.layoutId))
                .apply { setNewLayoutParams(LParam.MM) }
    }

    fun setMasterView(view: View){
        view.layoutParams = createFrameLP(LParam.MM)
        statusViewCache[Status.MASTER] = view
        changeStatus(Status.MASTER)
    }

    fun changeStatus(status: Status){
        currentStatus = status
        switchView()
    }

    private fun switchView() {
        val view = statusViewCache[currentStatus]
        if (view==null){
            "未找到对应的布局文件".logE()
            return
        }else{
            removeAllViews()
            addView(view)
        }
    }
}













