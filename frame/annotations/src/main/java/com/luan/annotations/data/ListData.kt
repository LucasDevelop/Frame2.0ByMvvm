package com.luan.annotations.data

/**
 * @package    com.luan.annotations.data
 * @author     luan
 * @date       2020/9/28
 * @des        用于在Bean类里出现多个List集合字段时指定用来做分页的数据字段
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class ListData