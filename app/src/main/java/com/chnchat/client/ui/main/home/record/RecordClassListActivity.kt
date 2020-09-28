package com.chnchat.client.ui.main.home.record

import android.content.Context
import android.content.Intent
import com.chnchat.client.R
import com.luan.base.design.ui.activity.BaseListActivity

/**
 * @package    RecordClassListActivity.kt
 * @author     luan
 * @date       2020/9/28
 * @des        课程录像列表
 */
class RecordClassListActivity : BaseListActivity<RecordClassListViewModel>() {

    companion object{
        fun launch(context: Context){
            context.startActivity(Intent(context,RecordClassListActivity::class.java))
        }
    }

    override val layoutId: Int = R.layout.activity_record_class_list

    override fun initView() {
    }

    override fun initData() {
    }


}