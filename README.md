# scalalaz-gen #

[![CI](https://github.com/scalalaz-podcast/scalalaz-gen/workflows/CI/badge.svg)](https://github.com/scalalaz-podcast/scalalaz-gen/actions)
[![Discord Server](https://img.shields.io/discord/632692119185653770)](https://discord.gg/RnugmrU)

Welcome to scalalaz-gen!

# Для редактирования в браузере через Vscode + metals используетй ссылку:

[https://gitpod.io/#https://github.com/scalalaz-podcast/scalalaz-gen/](https://gitpod.io/#https://github.com/scalalaz-podcast/scalalaz-gen/)

# Как делать новый выпуск

- страница выпуска
  
  Формируем заголовок как у остальных эпизодов (лежат `src/main/resources/md/series-*.md`):
  
  `title, audio.url`, page - апаем циферку
  
  `audio.length` - количество байт в mp3 (например `wc -c scalalaz-podcast-1.mp3`)
  
  Забираем с доки контент, переводим в md, не забываем про тэги `audioCountrols` и `discus`
  
- темы и вопросы - копипастим последнюю и апаем цифру внутри тоже
 
- генерим это все `sbt run`, все добро попадает в `target/site`
  
# Custom Markdown Tags #

@:audioControls "http://link-to-mp3-file.mp3"

- HTML audio controls attribute

@:disqus.

- Disqus based comments

## Contribution policy ##

Contributions via GitHub pull requests are gladly accepted from their original author. Along with any pull requests, please state that the contribution is your original work and that you license the work to the project under the project's open source license. Whether or not you state this explicitly, by submitting any copyrighted material via pull request, email, or other means you agree to license the material under the project's open source license and warrant that you have the legal authority to do so.

## License ##

This code is open source software licensed under the [Apache 2.0 License](http://www.apache.org/licenses/LICENSE-2.0.html).
