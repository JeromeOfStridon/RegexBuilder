# RegexBuilder

RegexBuilder is a framework helping developers to create regex programmatically, making this tedious task easier and more collaborative.
Framework comes along with RegexMatcher, extension of regular Matcher, but designed to match content against RegexBuilder and take full advantage of RegexBuilder features.



## Quickstart :
### 0. Foreword

This framework has been created to ease developers life, it tries to follow basic principles you should keep in mind to take best advantage of it:

** Intuitive ** 

Don't waist your time looking for all framework capacities, one single entry point : RegexFactory static class.
`RegexFactory.sequenceGroup()`, `RegexFactory.classMatch()`, `RegexFactory.regexMatcher()` etc.
If it's not here, it just doesn't exist at all !

Sample : 

```
RegexBuilder regexBuilder = RegexFactory.regexBuilder();
System.out.println(regexBuilder.toString());
```

** Verbose **

Concepts of regex are sometimes hard to get as they are using symbols (*, ?, +, |) you don't want to remember, let's use words instead !

Sample:

```
RegexBuilder regexBuilder = RegexFactory.regexBuilder();
regexBuilder.unique("Hello");

System.out.println(regexBuilder.toString());
```


** Easy coding **

The whole framework has been thought as a builder, start adding parts and just keep adding other parts to it !

```
RegexBuilder regexBuilder = RegexFactory.regexBuilder();
regexBuilder
	.anchorStart(true)
	.unique(CharacterClass.Space)
	.unique("World ")
	.some("!");

System.out.println(regexBuilder.toString());
```



** Collaborative **

Common regex are a nightmare to write, and a hell to read. Regular regex doesn't allow comments, and that's for the worst.
Fortunately, Regex Builder is made for you to code it, and code comes with comments right ? 
Please be gentle, code your regex, comment your regex, if not for you for your readers, that's the least you can do !

Sample:

```
RegexBuilder regexBuilder = RegexFactory.regexBuilder();
regexBuilder.unique("."); // expecting phrase to end with dot

System.out.println(regexBuilder.toString());
```


** DRY (Don't repeat yourself) **

When building a complex regex, you may face the case of reusing several identical pieces. 
RegexBuilder framework has been designed to work with composition, for example in such situation you could easily create one group per piece, and then use these group several times in your RegexBuilder.

```
RegexBuilder sentence

```

Same applies to ClassMatch.

### 1. RegexBuilder

RegexBuilder is the root and delivery of your work, it will embed all parts of your regex such as groups, string match, class match etc


### 2. Groups


#### 2.1. Generic

A group is a piece of your regex, it can just be a simple character, a string, a class of characters, or a composition of groups.


##### 2.1.1. Quantities

For each group you should define how much you want of it in your final expression, should it be 
- optional : 0 or 1
- unique : just one
- some : 1 or as many as you want
- any : 0 or as many as you want
- between : choose your limits
- exactly : you should have get it this far right ?


##### 2.1.2. Sequential / Alternative

Groups can have two different directions to be seen : as pieces to be followed one by one (sequence), or as pieces to be seen as options compared to other (alternative)

##### 2.1.3. Capturing / Non Capturing

Regex offers you to extract pieces of content to be matched, also called as captures, define if your group is a capturing one or not.
If you are to create capturing groups, please consider giving it a name ! you will then be able to call the matched content by its name instead of calculating its position within your whole regex !

##### 2.1.4. Look Ahead / Look Behind

These groups will enable your regex to check content before of after the match you want to get.



** Groups can have different shapes such as following : **

#### 2.2. ClassMatch Group
Kind of group enabling you to match a range of characters defined in generic way (numeric, uppercase letters, lowercase letters, mixed, or specific character range)

##### 2.2. StringMatch Group
Simple way of saying "I want to match that sequence of characters"

### 3. RegexMatcher

RegexBuilder is made for you to create regex, RegexMatcher is made for you to match content 

`RegexMatcher regexMatcher = new RegexMatcher(regexBuilder, "Hello world !");`

Regular Matcher class methods have been wrapped in RegexMatcher :
- `RegexMatcher::groupCount()`
- `RegexMatcher::group()`
- `RegexMatcher::group(int groupId)`
- `RegexMatcher::start()`
- `RegexMatcher::start(int groupId)`
- `RegexMatcher::end()`
- `RegexMatcher::end(int groupId)`



##### 3.1. Groups

Groups declared with names in RegexBuilder can be called directly in RegexMatcher
`RegexMatcher::group(String groupName);`

Several methods of original Matcher object are wrapped and overriden to accept group name as parameter instead of group index :
- `RegexMatcher::group(String groupName)`
- `RegexMatcher::start(String groupName)`
- `RegexMatcher::end(String groupName)`

Complementary features have been added to be used with group names !
- Getting mapped content group names and matched content :  `RegexMatcher::groupContentMap()`
- Replacing content by its group name : `RegexMatcher::replace(String groupName, String replacementString`


##### 3.2. RegexMatch
When having extensive usage of matchers, `start()`, `end()` and `group()` may force you to store  


### 4. Library & Samples
Framework is shipped with RegexBuilder templates you can already use in your regex, as such, or as parts. Make good use of it !

Sample : 

```
RegexBuilder hhMMClock = RegexFactory.regexBuilder();
hhMMClock
	// Hours
	.unique(
			RegexFactory.alternativeGroup()
			.unique(RegexFactory.sequenceGroup()
				.unique(RegexFactory.classMatch('0','1'))
				.unique(CharacterClass.Numeric))
			.unique(RegexFactory.sequenceGroup()
				.unique("2")
				.unique(RegexFactory.classMatchRange('0', '3'))
				)
		)
	// Separator
	.unique(":")
	// Minutes
	.unique(RegexFactory.classMatchRange('0', '5'))
	.unique(CharacterClass.Numeric);
```

### 5. Advanced features

** Anchors **

Regular regex can specify to have starting and ending anchors (specifying if regex match should start at first content character and end at the last content character)
Such parameters can be activated on RegexBuilder with `RegexBuilder::anchorStart(boolean anchorStart)` and `RegexBuilder::anchorEnd(boolean anchorEnd)`.

** Manual Matching **

If you don't want to use RegexMatcher to match content against your RegexBuilder, you may use :

- `String RegexBuilder::toString()` to get regular regex out 
- `Pattern RegexBuilder::compile()` to compile your RegexBuilder into a Pattern

** Recycle **

We strongly encourage you to embed RegexBuilder in other RegexBuilder, to do so you shall convert your `RegexBuilder` to a `Group` using `Group RegexBuiler::asGroup()`
Doing so will result of losing RegexBuilder specific properties : `anchorStart` and `anchorEnd`.



### 5. License
The source code is licensed under the MIT license, which you can find in the MIT-LICENSE.txt file.



	