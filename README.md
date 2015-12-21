



# Using intellij

After you've run `sbt scrooge-gen` the thrift definitions will be used to generate
scala sources. These will be in `thrift-interfaces/target/scala-2.11/src_managed`.
You may need to configure your intellij project to recognise these new sources.
In your project settings (`cmd+;`) select the modules section from the left menu
and the *thriftInterfaces* project.
Uncheck `target` from the excluded list, and add everything below target back
to the excluded list with the exception of `scala-2.x`.
