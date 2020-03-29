package com.example.myrealtripchallenge.dto

import org.simpleframework.xml.*

@Root(name = "rss", strict = false)
data class RssData(@field:Element(name = "channel")
                   @param:Element(name = "channel")
                   val info: Info? = null)

@Root(name = "channel", strict = false)
data class Info @JvmOverloads constructor(
    @field:ElementList(entry = "item", inline = true)
    @param:ElementList(entry = "item", inline = true)
    val news: List<Item>? = null,
    @field:Element(name = "link")
    @param:Element(name = "link")
    val link: String? = null)

@Root(name = "item", strict = false)
data class Item @JvmOverloads constructor(
    @field:Path("link")
    @field:Text(required = false)
    @param:Path("link")
    @param:Text(required = false)
    val link: String? = null,
    @field:Path("description")
    @field:Text(required = false)
    @param:Path("description")
    @param:Text(required = false)
    val description: String? = null,
    @field:Path("title")
    @field:Text(required = false)
    @param:Path("title")
    @param:Text(required = false)
    val title: String? = null,
    @field:Path("pubDate")
    @field:Text(required = false)
    @param:Path("pubDate")
    @param:Text(required = false)
    val pubDate: String? = null)

