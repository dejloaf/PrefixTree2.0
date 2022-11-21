import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TreeTest {
    @Mock
    PrefixTree t = new PrefixTree();

    @Before
    public void setUp(){
        t.addAll("cat", "rat", "rad", "bad", "noob", "nools", "fox",
                "fix", "list", "medium", "crispy", "crud", "croops", "java", "math", "laboratory", "nods", "noders");
    }

    @Test
    public void getSize(){
        assertEquals(0, t.size());
    }

    @Test
    public void getRoot(){
        assertNull(t.root());
    }

    @Test
    public void createNode(){
        PrefixTree.Node newNode = Mockito.mock(PrefixTree.Node.class);
        assertNull(newNode.value);
        assertNull(newNode.next);
    }

    @Test
    public void createTypeAddition(){
        PrefixTree.TypeAddition newAddition = Mockito.mock(PrefixTree.TypeAddition.class);
        assertEquals(0, newAddition.index);
        assertNull(newAddition.substring);
        assertFalse(newAddition.splitFlag);
    }

    @Test
    public void getAllWords(){
        t.addAll("java", "math", "laboratory", "nods", "noders");
        List<String> lst = Arrays.asList("laboratory", "noders", "nods", "math", "java");
        assertNotEquals(0, t.size());
        assertEquals(lst, PrefixTree.getAllWords());
    }

    @Test
    public void removeWordsByPrefix(){
        t.addAll("java", "math", "laboratory", "nods", "noders");
        assertFalse(t.removeWordsByPrefix("no"));
    }

    @Test
    public void contains(){
        t.addAll("java", "math", "laboratory");
        assertTrue(t.contains("java"));
    }

}