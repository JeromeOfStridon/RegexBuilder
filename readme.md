# Regex Builder

Regex Builder is a framework helping developers to create regex programmatically, making this tedious task easier and more collaboratively.
Framework also provides Regex Matcher, extension of regular matcher,  working with Regex Builder and exploiting all the power nt with regex created with RegexBuilder !

Example :



## Quickstart :
### 0. Foreword

Creating this framework we tried to follow simple directions: 

** Intuitive ** 

Don't waist your time looking for all framework capacities, one single entry point : RegexFactory static class.
RegexFactory.sequenceGroup() RegexBuilder.classMatch() etc.
If it's not here, it just doesn't exist at all !

`RegexBuilder regexBuilder = RegexFactory.regexBuilder();`

** Fluent **

Concepts of regex are sometimes hard to get as they are using symbols (*, ?, +, |) you don't want to remember, let's use words instead !

`regexBuilder.unique("Hello");`


** Easy coding **

The whole framework has been thought as a builder, start adding parts and just keep adding other parts to it !

`regexBuilder.unique(CharacterClass.Space).unique("World ").some("!");`



** Collaborative **

Common regex are a nightmare to write, and a hell to read. Regular regex doesn't allow comments, and that's for the worst.
Fortunately, Regex Builder is made for you to code it, and code comes with comments right ? 
Please be gentle, code your regex, comment your regex, if not for you for your readers, that's the least you can do !
`regexBuilder.unique(".") // expecting phrase to end with dot`


** DRY (Don't repeat yourself) **

Create regex using object oriented programming, using composition of objects that can be used in several regex, or objects called multiple times in the same regex !

RegexBuilder sentence


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


##### 3.1. Group names

Groups declared with names in RegexBuilder can be called directly in RegexMatcher
`regexMatcher.group("Greeting");`

You can also use `start` and `end` methods that would give you the same result as classic Matcher.

##### 3.2. Replace

Looks for group and replace it with specified content


##### 3.3. RegexMatch



### 4. Library
Framework is shipped with RegexBuilder templates you can already use in your regex, as such, or as parts. Make good use of it !

Example : 

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

### 5. Advanced options

** Anchors **




### 5. License
The source code is licensed under the MIT license, which you can find in the MIT-LICENSE.txt file.


### 6. TO CLEAN
- Regex as code :
	you can comment your code to explain the way you created the regex rule by rule
	readable methods enabling you to read instead of decode the regex

- Intuitive : 
	beXXX sets the configuration of each object
	any, unique, some, between, exactly, optional, none ?
- Regex optimizer included :
	Created to try to simplify your regex as much as possible


- think about using beXXX => 
	- beNegativeLookaheadGroup
	- beSequential / beAlternative
	- beCapturing(capturingGroupName)
	