# Mose Stream

A streaming library for Java 8 and above.

## Why not java.utils.Stream?

Valid question, the main motivation was error handling within the stream. For example the following is using 
java.utils.Streams with the next example using MoseStream

### Java Stream

```java

Stream<String> stream;
try{
    List<String> result = stream
            .filter(value -> {
                try {
                    return someMethod();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            })
            .collect(Collectors.toList());
}catch(RuntimeException e1){
    IOException ex = (IOException)e1.getCause();
    //handle
}

```
### Mose Stream

```java
MoseStream<String> stream;
try{
    List<String> result = stream
            .filter(value -> someMethod())
            .collect(Collectors.toList());
}catch(IOException e){
    //handle
}

```

## Features

- Only requires a Iterable
- Error handling within the iterator
- fast (same processing method as Java Stream and C# Linq)
- extra stream methods compared to java stream
> - Group By
> - Cast
> - ToSet (ToList too)
