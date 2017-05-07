package io.github.igordonxiao

import org.apache.catalina.authenticator.jaspic.AuthConfigFactoryImpl
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.security.auth.message.config.AuthConfigFactory
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@SpringBootApplication
@Controller
open class CutterServerApplication {
    private val datetimeFormatter = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")
    @Value("\${upload.path}")
    private val uploadPath: String? = null
    private val IMG_EXT = ".png"

    @RequestMapping("/")
    fun index(): String {
        return "index"
    }

    @RequestMapping("/ctl")
    fun ws(): String {
        return "ctl"
    }

    @RequestMapping(value = "upload", method = arrayOf(RequestMethod.POST))
    @ResponseBody
    fun uploadAction(request: HttpServletRequest, @RequestParam("file") file: MultipartFile, response: HttpServletResponse): Int {
        val fileName = uploadPath + datetimeFormatter.format(Date()) + "-" + (UUID.randomUUID().toString()) + IMG_EXT
        file.transferTo(File(fileName))
        return 0
    }
}

fun main(args: Array<String>) {
    if (AuthConfigFactory.getFactory() == null) AuthConfigFactory.setFactory(AuthConfigFactoryImpl())
    SpringApplication.run(CutterServerApplication::class.java, *args)
}
