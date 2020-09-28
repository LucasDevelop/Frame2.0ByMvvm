package com.luan.compiler

import com.google.common.collect.ImmutableSet
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec
import sun.rmi.runtime.Log
import java.lang.Exception
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.PackageElement
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

/**
 * @package    com.luan.compiler
 * @author     luan
 * @date       2020/9/14
 * @des
 */
abstract class BaseProcessor : AbstractProcessor() {

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return ImmutableSet.of(getSupportedType().canonicalName)
    }

    //支持的类型
    abstract fun  getSupportedType(): Class<out Annotation>

    //文件创建
    abstract fun createFile(element: Element):TypeSpec

    override fun process(annnotations: MutableSet<out TypeElement>?, env: RoundEnvironment?): Boolean {
        env?.getElementsAnnotatedWith(getSupportedType())?.forEach {element ->
            val createFile = createFile(element)
            try {
                //获取包名
                var ele = element
                while (ele !is PackageElement){
                    ele = element.enclosingElement
                }
                if (ele is PackageElement){
                    val builder = JavaFile.builder(ele.qualifiedName.toString(), createFile)
                    builder.build().writeTo(processingEnv.filer)
                }
            }catch (e:Exception){
                e.printStackTrace()
                printE("Host 生产失败")
            }
        }
        return false
    }

    fun println(msg:String){
        processingEnv.messager.printMessage(Diagnostic.Kind.NOTE,msg)
    }

    fun printE(msg:String){
        processingEnv.messager.printMessage(Diagnostic.Kind.ERROR,msg)
    }
}