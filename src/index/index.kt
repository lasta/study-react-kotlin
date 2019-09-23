package index

import app.*
import kotlinext.js.*
import react.dom.*
import kotlin.browser.*
import kotlin.js.RegExp

fun main(args: Array<String>) {
    requireAll(require.context("src", true, js("/\\.css$/") as RegExp))

    render(document.getElementById("root")) {
        app()
    }
}
