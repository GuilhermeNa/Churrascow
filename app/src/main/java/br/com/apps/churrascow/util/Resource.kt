package br.com.apps.churrascow.util

data class Resource<T>(

    var t: T?,
    var error: String? = null

) {

    fun setError(t: T, error: String?) {
        t?.let {
            this.t = t
        }
        error?.let {
            this.error = error
        }
    }

}