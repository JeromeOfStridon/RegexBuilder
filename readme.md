# Regex Builder

Regex Builder is a framework helping developers to create regex programmatically, making this tedious task easier and more collaboratively.
Framework also provides Regex Matcher, extension of regular matcher,  working with Regex Builder and exploiting all the power nt with regex created with RegexBuilder !

Example :



## Quickstart :
### 0. Foreword

Creating this framework we tried to follow simple directions: 

** DRY (Don't repeat yourself) **

You can build your regex with one single object, or as a composition of different objects you can reuse.
You may reuse the same object several times in your regex, or build your own library of regex parts.


** Easy to use ** 

When building your regex, you may need different tools that are splitted in different classes. 
In order to avoid too much discovery efforts, every tool you may want to use to build your regex are available statically in RegexBuilder class ! 
RegexBuilder.sequenceGroup() RegexBuilder.classMatch() etc
Don't waste your time ! If it's not here, it just doesn't exist at all !


** Collaborative **

Common regex are a nightmare to write, but a hell to read. Regular regex doesn't allow comments, and that's for the worst.
Fortunately, with Regex Builder, as you write your regex with code, you can add comments, please do so.




### 1. Matching parts
A regex is built out of little pieces that will match parts of the string you apply your regex to, and a structure that assemble them.
Two tools are at your disposal to build these pieces : StringMatch & ClassMatch

** StringMatch **

Use this tool if you want to match specific strings, this is the simplest tool you 

`RegexBuilder regexBuilder = new RegexBuilder();`
`regexBuilder.unique(RegexBuilder.stringMatch("I like"));`


** ClassMatch **

Use this tool to declare characters that can be matched
You can declare a range of characters directly from predefined CharacterClass enum
You can also declare specific characters

### 2. Parts assembly 
** Groups **
Once you have the pieces of your regex, you need a structure to assamble them.
- Sequence Group to explain how pieces are supposed to follow each other.
- Alternative Group to open different parallel options

** Quantities **
All of the pieces created before can be specified to be matched

- any : zero or more
- some : one or more
- unique : one and only one
- between : 


** Sequence Group & Alternative Group **

### 3. Group Options

** Capturing **

** Lookahead / Lookbehind / etc **

### 4. RegexBuilder options

Some options available at regexbuilder level only
- anchorStart
- anchorEnd

### 4. RegexMatcher

** group() **


** replace() **


### 5. License
See [LICENSE] for Regex Builder license.

[LICENSE]: https://github.com/projectlombok/lombok/blob/master/LICENSE
