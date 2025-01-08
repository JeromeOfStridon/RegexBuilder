package com.regexbuilder.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.regexbuilder.ClassMatch.CharacterClass;
import com.regexbuilder.Group;
import com.regexbuilder.Group.GroupType;
import com.regexbuilder.Group.TreeType;
import com.regexbuilder.Regex;
import com.regexbuilder.RegexBuilder;



public class RegexTest {
	
	@Test
	public void emptyTest() {
		Regex regex = RegexBuilder.regex();
		assertEquals("", regex.toString());
	}
	
	@Test
	public void anchorsTest() {
		Regex regex = RegexBuilder.regex()
			.anchorStart(true)
			.anchorEnd(true)
			.any("a");
		
		assertEquals("^a*$", regex.toString());
		assertTrue(regex.isAnchorStart());
		assertTrue(regex.isAnchorEnd());
		
		regex.setAnchorStart(false);
		assertFalse(regex.isAnchorStart());
		assertTrue(regex.isAnchorEnd());
		
		
		regex.setAnchorEnd(false);
		assertFalse(regex.isAnchorStart());
		assertFalse(regex.isAnchorEnd());
		
		
	}
	
	@Test
	public void testRegexBuilderTreeTypeConstructor() {
		Regex rb = RegexBuilder.regex(TreeType.Alternative);
		rb.unique("A").exactly("B", 2);
		
		assertEquals("A|B{2}", rb.toString());
	}
	
	@Test
	public void testRegexBuilderGroupTypeConstructor() {
		Regex rb = RegexBuilder.regex(GroupType.NonCapturing);
		rb.unique("A").exactly("B", 2);
		
		assertEquals("(?:AB{2})", rb.toString());
	}
	
	@Test
	public void testRegexBuilderTreeTypeGroupTypeConstructor() {
		Regex regex = RegexBuilder.regex(TreeType.Alternative, GroupType.NonCapturing);
		regex.unique("A").exactly("B", 2);
		
		assertEquals("(?:A|B{2})", regex.toString());
	}
	
	@Test
	public void testRegexBuilderMultiGroup() {

		Group group = RegexBuilder.sequenceGroup()
				.unique(CharacterClass.Alphabetic);
		
		Regex rb = RegexBuilder.regex(TreeType.Sequence);
		
		rb.exactly(group, 3);
		rb.unique("-");
		rb.exactly(group, 2);
		
		assertEquals("[a-zA-Z]{3}-[a-zA-Z]{2}", rb.toString());  
	}

	@Test
    public void testClone() {
        Regex original = RegexBuilder.regex()
            .anchorStart(true)
            .unique("test")
            .any(CharacterClass.Numeric);
            
        Regex cloned = original.clone();
        
        assertEquals(original.toString(), cloned.toString());
        assertTrue(cloned.isAnchorStart());
        assertNotSame(original, cloned);
    }

    @Test
    public void testCompile() {
        Regex regex = RegexBuilder.regex()
            .unique("abc")
            .some(CharacterClass.Numeric);
            
        assertNotNull(regex.compile());
        assertEquals("abc[0-9]+", regex.compile().pattern());
    }

    @Test 
    public void testChainedQuantifiers() {
        Regex regex = RegexBuilder.regex()
            .min(CharacterClass.Alphabetic, 2)
            .max(CharacterClass.Numeric, 3)
            .between(CharacterClass.Any, 1, 4);
            
        assertEquals("[a-zA-Z]{2,}[0-9]{,3}.{1,4}", regex.toString());
    }

    @Test
    public void testLazyQuantifiers() {
        Regex regex = RegexBuilder.regex()
            .minLazy(CharacterClass.Alphabetic, 2)
            .maxLazy(CharacterClass.Numeric, 3)
            .betweenLazy(CharacterClass.Any, 1, 4);
            
        assertEquals("[a-zA-Z]{2,}?[0-9]{,3}?.{1,4}?", regex.toString());
    }

    @Test
    public void testComplexGrouping() {
        Regex regex = RegexBuilder.regex(TreeType.Alternative, GroupType.NonCapturing)
			.unique(RegexBuilder.sequenceGroup()
                .some(CharacterClass.Alphabetic)
                .unique("-")
                .some(CharacterClass.Numeric))
			.unique(RegexBuilder.sequenceGroup()
                .some(CharacterClass.Alphanumeric)
                .unique("@"));
        
        assertEquals("(?:[a-zA-Z]+-[0-9]+|[a-zA-Z0-9]+@)", regex.toString());
    }

    @Test
    public void testAnchorCombinations() {
        Regex regex = RegexBuilder.regex()
            .anchorStart(true)
            .unique("test")
            .anchorEnd(true);
            
        assertEquals("^test$", regex.toString());
        
        regex.anchorStart(false);
        assertEquals("test$", regex.toString());
        
        regex.anchorEnd(false);
        assertEquals("test", regex.toString());
        
        regex.anchorEnd(true);
        assertEquals("test$", regex.toString());
    }

	@Test
    public void testGroupNameAndPosition() {
        Regex regex = RegexBuilder.regex()
            .unique(RegexBuilder.sequenceGroup()
                .setName("first")
                .some(CharacterClass.Alphabetic))
            .unique("-")
            .unique(RegexBuilder.sequenceGroup()
                .setName("second")
                .some(CharacterClass.Numeric));
            
        assertEquals("([a-zA-Z]+)-([0-9]+)", regex.toString());
        assertEquals(Integer.valueOf(1), regex.findGroupPosition("first"));
        assertEquals(Integer.valueOf(2), regex.findGroupPosition("second"));
        assertEquals(2, regex.getGroupPositions().size());
    }

    @Test
    public void testAsGroup() {
        Regex regex = RegexBuilder.regex()
            .unique("test")
            .some(CharacterClass.Numeric);
            
        Group group = regex.asGroup();
        assertEquals(regex.toString(), group.toString());
        assertNotSame(regex, group);
    }

    @Test
    public void testQuantifierMethods() {
        Regex regex = RegexBuilder.regex()
            .optional(CharacterClass.Alphabetic)
            .some(CharacterClass.Numeric)
            .any(CharacterClass.Whitespace)
            .exactly(CharacterClass.Word, 3)
            .min(CharacterClass.Digit, 2)
            .max(RegexBuilder.classMatch('.', '!', '?'), 4)
            .between(CharacterClass.Any, 1, 5);
            
        assertEquals("[a-zA-Z]?[0-9]+\\s*\\w{3}\\d{2,}[.!?]{,4}.{1,5}", regex.toString());
    }

    @Test
    public void testLazyQuantifierMethods() {
        Regex regex = RegexBuilder.regex()
            .optionalLazy(CharacterClass.Alphabetic)
            .someLazy(CharacterClass.Numeric)
            .anyLazy(CharacterClass.Whitespace)
            .minLazy(CharacterClass.Word, 2)
            .maxLazy(RegexBuilder.classMatch('.', '!', '?'), 4)
            .betweenLazy(CharacterClass.Any, 1, 5);
            
        assertEquals("[a-zA-Z]??[0-9]+?\\s*?\\w{2,}?[.!?]{,4}?.{1,5}?", regex.toString());
    }

    @Test
    public void testNestedGroups() {
        Regex regex = RegexBuilder.regex()
            .unique(RegexBuilder.sequenceGroup()
                .unique(RegexBuilder.alternativeGroup()
                    .unique("abc")
                    .unique("def"))
                .some(CharacterClass.Numeric))
            .unique("-")
            .unique(RegexBuilder.alternativeGroup()
                .unique("xyz")
                .unique("123"));
            
        assertEquals("(abc|def)[0-9]+-(xyz|123)", regex.toString());
    }

    @Test
    public void testLookAroundGroups() {
        Regex regex = RegexBuilder.regex()
            .unique(RegexBuilder.sequenceGroup()
                .setGroupType(GroupType.PositiveLookAhead)
                .unique("foo"))
            .unique(RegexBuilder.sequenceGroup()
                .setGroupType(GroupType.NegativeLookBehind)
                .unique("bar"))
            .some(CharacterClass.Word);
            
        assertEquals("(?=foo)(?<!bar)\\w+", regex.toString());
    }

    @Test
    public void testMixedCharacterClasses() {
        Regex regex = RegexBuilder.regex()
            .some(RegexBuilder.classMatch(
                CharacterClass.Alphabetic,
                CharacterClass.Numeric)
                .add('$')
                .add('@'));
            
        assertEquals("[a-zA-Z0-9$@]+", regex.toString());
    }

    @Test
    public void testComplexEmail() {
        Regex regex = RegexBuilder.regex()
            .anchorStart(true)
            .some(CharacterClass.AlphabeticLower)
            .optional(RegexBuilder.sequenceGroup()
                .unique(".")
                .some(CharacterClass.AlphabeticLower))
            .unique("@")
            .some(CharacterClass.AlphabeticLower)
            .unique(".")
            .between(CharacterClass.AlphabeticLower, 2, 4)
            .anchorEnd(true);
            
        assertEquals("^[a-z]+(\\.[a-z]+)?@[a-z]+\\.[a-z]{2,4}$", regex.toString());
    }

    @Test
    public void testPhoneNumber() {
        Regex regex = RegexBuilder.regex()
            .optional("(")
            .exactly(CharacterClass.Numeric, 3)
            .optional(")")
            .optional(CharacterClass.Whitespace)
            .exactly(CharacterClass.Numeric, 3)
            .unique("-")
            .exactly(CharacterClass.Numeric, 4);
            
        assertEquals("\\(?[0-9]{3}\\)?\\s?[0-9]{3}-[0-9]{4}", regex.toString());
    }

	@Test
    public void testMultipleLookAroundGroups() {
        Regex regex = RegexBuilder.regex()
            .unique(RegexBuilder.sequenceGroup()
                .setGroupType(GroupType.PositiveLookAhead)
                .unique("password"))
            .unique(RegexBuilder.sequenceGroup()
                .setGroupType(GroupType.NegativeLookBehind)
                .unique("123"))
            .unique(RegexBuilder.sequenceGroup()
                .setGroupType(GroupType.NegativeLookAhead)
                .unique("simple"))
            .some(CharacterClass.Word);
            
        assertEquals("(?=password)(?<!123)(?!simple)\\w+", regex.toString());
    }

    @Test
    public void testNestedAlternativeGroups() {
        Regex regex = RegexBuilder.regex()
            .unique(RegexBuilder.alternativeGroup()
                .unique(RegexBuilder.alternativeGroup("cat", "dog"))
                .unique(RegexBuilder.alternativeGroup("bird", "fish")))
            .some(CharacterClass.Whitespace);
            
        assertEquals("((cat|dog)|(bird|fish))\\s+", regex.toString());
    }

    @Test
    public void testComplexQuantifierCombinations() {
        Regex regex = RegexBuilder.regex()
            .between(CharacterClass.Alphabetic, 2, 4)
            .minLazy(CharacterClass.Numeric, 3)
            .exactly(CharacterClass.Whitespace, 2)
            .maxLazy(CharacterClass.Word, 5);
            
        assertEquals("[a-zA-Z]{2,4}[0-9]{3,}?\\s{2}\\w{,5}?", regex.toString());
    }

    @Test
    public void testMixedGroupsWithQuantifiers() {
        Regex regex = RegexBuilder.regex()
            .unique(RegexBuilder.sequenceGroup()
                .setGroupType(GroupType.NonCapturing)
                .some(CharacterClass.Alphabetic))
            .between(RegexBuilder.alternativeGroup()
                .unique("@")
                .unique("#"), 1, 2)
            .exactly(RegexBuilder.sequenceGroup()
                .unique(".")
                .some(CharacterClass.AlphabeticLower), 2);
                
        assertEquals("(?:[a-zA-Z]+)(@|#){1,2}(\\.[a-z]+){2}", regex.toString());
    }

    @Test
    public void testDateTimePattern() {
        Regex regex = RegexBuilder.regex()
            .exactly(CharacterClass.Numeric, 4)
            .unique("-")
            .between(CharacterClass.Numeric, 1, 2)
            .unique("-")
            .between(CharacterClass.Numeric, 1, 2)
            .optional(RegexBuilder.sequenceGroup()
                .unique("T")
                .exactly(CharacterClass.Numeric, 2)
                .unique(":")
                .exactly(CharacterClass.Numeric, 2)
                .unique(":")
                .exactly(CharacterClass.Numeric, 2));
                
        assertEquals("[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}(T[0-9]{2}:[0-9]{2}:[0-9]{2})?", regex.toString());
    }

    @Test
    public void testClassMatchQuantifiers() {
        Regex regex = RegexBuilder.regex();
        regex.anyLazy(RegexBuilder.classMatch()
                .add(CharacterClass.Alphabetic)
				.add('-', '_'))
            .someLazy(RegexBuilder.classMatch()
                .addRange('0', '9'))
            .optionalLazy(RegexBuilder.classMatch()
                .add('!', '?', '.'));

        assertEquals("[a-zA-Z\\-_]*?[0-9]+?[!?.]??", regex.toString());
    }

    @Test 
    public void testMixedClassMatchAndCharacterClass() {
        Regex regex = RegexBuilder.regex();
        regex.any(RegexBuilder.classMatch()
                .add(CharacterClass.AlphabeticUpper)
                .add('.'))
            .anyLazy(CharacterClass.Numeric)
            .some(RegexBuilder.classMatch()
                .add(CharacterClass.Word)
                .addRange('a', 'f'));

        assertEquals("[A-Z.]*[0-9]*?[\\wa-f]+", regex.toString());
    }

    @Test
    public void testNestedClassMatchInGroups() {
        Regex regex = RegexBuilder.regex();
        regex.unique(RegexBuilder.sequenceGroup()
                .anyLazy(RegexBuilder.classMatch()
                    .add(CharacterClass.AlphabeticLower)
                    .add('-'))
                .setName("username"))
            .unique("@")
            .unique(RegexBuilder.sequenceGroup()
                .some(RegexBuilder.classMatch()
                    .add(CharacterClass.Alphanumeric)
                    .add('.'))
                .setName("domain"));

        assertEquals("([a-z\\-]*?)@([a-zA-Z0-9.]+)", regex.toString());
    }

    
	

}
