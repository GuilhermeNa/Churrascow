package br.com.apps.churrascow.ui.fragments.baseFragment

interface IdConsumer<T> {

    /**
     *This interface is responsible for making the id available for consumption as soon as it has
     * been loaded internally.
     *
     * @return id of a giver object
     */
    fun idConsumer(id: T)

}