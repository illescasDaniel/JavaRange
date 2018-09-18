# JavaRange
A class to easily manage ranges of values. Includes methods like "contains", "size", "toList", etc.

Dependencies:
--
```
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.2</version>
    <scope>provided</scope>
</dependency>
```
Examples
--
```java
// Sum of prime numbers contained in the closed range [0, 100] with stride of 2
Range.to(100, 2).stream()
	.filter(value -> isPrime(value))
	.reduce(0L, Long::sum)
```
```java	
// Value is between a range
if (new Range(13, 9834).contains(randomNumber)) {
	...
}
```