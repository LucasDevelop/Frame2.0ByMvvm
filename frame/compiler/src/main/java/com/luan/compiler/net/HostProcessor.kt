package com.luan.compiler.net

import com.google.auto.service.AutoService
import com.luan.annotations.net.Host
import com.luan.compiler.BaseProcessor
import com.squareup.javapoet.TypeSpec
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement

/**
 * @package    com.luan.compiler.net
 * @author     luan
 * @date       2020/9/14
 * @des
 */
//@AutoService(Process::class)
//class HostProcessor : BaseProcessor() {
//
//    override fun getSupportedType(): Class<out Annotation> = Host::class.java
//
//    //fixme
//    override fun createFile(element: Element): TypeSpec = TypeSpec.classBuilder("").build()
//
//}