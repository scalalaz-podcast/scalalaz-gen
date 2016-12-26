# scalalaz-gen #

Welcome to scalalaz-gen!

# Как делать новый выпуск

- страница выпуска
  
  Формируем заголовок как у остальных эпизодов (лежат `src/main/resources/md/series-*.md`):
  
  `title, enc.url`, page - апаем циферку
  
  `enc.length` - количество байт в mp3 (например `wc -c scalalaz-podcast-1.mp3`)
  
  Забираем с доки контент, переводим в md, не забываем про тэги `audioCountrols` и `discus`
  
- темы и вопросы - копипастим последнюю и апаем цифру внутри тоже
 
- генерим это все `sbt run`, все добро попадает в `target/site`
  
# Custom Markdown Tags #

@:audioControls "http://link-to-mp3-file.mp3"

- HTML audio controls attribute

@:disqus.

- Disqus based commnets

### Rss

Rss has special start and end marks:
```
@:rssTagStart tag=title:
    some content
@:rssTagEnd tag=title.
```
'title' here is a name of rss tag.

content between marks should has indent.  

If this tag need to has attributes it should specified by this way:
```
@:rssTagStart tag=enclosure attr="url(/mp3/scalalaz-podcast-3mp3);type(audio/mpeg)":{}
@:rssTagEnd tag=enclosure.
```

'url(text)' == url="text"
';' - is a separator between attributes


## Contribution policy ##

Contributions via GitHub pull requests are gladly accepted from their original author. Along with any pull requests, please state that the contribution is your original work and that you license the work to the project under the project's open source license. Whether or not you state this explicitly, by submitting any copyrighted material via pull request, email, or other means you agree to license the material under the project's open source license and warrant that you have the legal authority to do so.

## License ##

This code is open source software licensed under the [Apache 2.0 License](http://www.apache.org/licenses/LICENSE-2.0.html).
