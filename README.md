A small project comparing performance of different templating engines for a very simple usecase.

Tested engines:

[freemarker](http://freemarker.org/)

[mustache](https://github.com/spullara/mustache.java)

[velocity](http://velocity.apache.org/)

[StringTemplate](http://www.stringtemplate.org/)


_Please note: this test is done with ONLY OUR requirements in mind, your mileage may vary_

Our requirements were:

- Renders plain text templates (that's why we didn't use thymeleaf)
- uses templates from String
- doesn't require a web container (messages are rendered inside Vert.x and propagated using the event bus)


Numbers in ms for 10000000 renderings on my machine (MBP 13" 2014):

- FreemarkerTest elapsed 3711
- MustacheTest elapsed 865
- StringTemplateTest elapsed 10264
- VelocityTest elapsed 3328