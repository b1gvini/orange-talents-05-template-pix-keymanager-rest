package br.com.zup.edu.vini

import io.micronaut.runtime.Micronaut.*
fun main(args: Array<String>) {
	build()
	    .args(*args)
		.packages("br.com.zup.edu.vini")
		.start()
}

