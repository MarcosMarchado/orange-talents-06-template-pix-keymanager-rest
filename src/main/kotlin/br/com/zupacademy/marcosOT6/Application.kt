package br.com.zupacademy.marcosOT6

import io.micronaut.runtime.Micronaut.*
fun main(args: Array<String>) {
	build()
	    .args(*args)
		.packages("br.com.zupacademy.marcosOT6")
		.start()
}

