title=Issue 55 - Mini QA with John De Goes [EN] [5 min]
audio.url=https://scalalaz.ru/mp3/scalalaz-podcast-55.mp3
audio.length=8746208
page=https://scalalaz.ru/series-55.html
date=2018-12-04
----

![episode 55](https://scalalaz.ru/img/episode55.jpg)

<br/>
Cats-effect library lets us implement modules parameterized with an abstract effect F bounded by Sync, Async, etc capabilities, i.e. not choose a concrete effect monad until the very end of the universe.

In opposite, AFAIU, John's approach is different. We still describe our algebras with F, but have to implement modules with IO (zio), which doesn't let us easily migrate to a different IO monad later with a simple replace in the Main class.

Does John consider it as a weak side of zio? Or such a lib is not even needed if you use the best io impl like ZIO? )))

I know he has proposed some ideas for Cats Effect 2.0. Does he believe he'll be heard by Nedelcu & co?

- [Cats Effect 2.0 Design Discussion](https://github.com/typelevel/cats-effect/issues/321)
<br/>

Библиотека Cats-effect позволяет реализовывать модули, параметризованные с помощью *абстрактного* эффекта F, ограниченного
возможностями Sync, Async и т.д, т.е библиотека позволяет откладывать выбор конкретного
эффекта до самого конца.

Подход Джона противоположный, он описывает алгебры с помощью F
но заставляет реализовать модули с конкретным IO (zio), что не позволяет легко мигрировать на другую монаду.

Считает ли Джон, что это недостаток zio? Или ZIO настолько хорошая имплементация IO, что другие библиотеки не нужны?

Как известно, он предложил несколько идея для Cats Effect 2.0. -
верит ли он, что будет услышаны Nedelcu & co ?

<br/>
Patreon [https://www.patreon.com/scalalalaz](https://www.patreon.com/scalalalaz)
<br/>

Hosts:
[Oli Makhasoeva](https://twitter.com/oli_kitty),
[John De Goes](https://twitter.com/jdegoes)
