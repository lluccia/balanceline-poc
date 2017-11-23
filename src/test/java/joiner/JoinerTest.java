package joiner;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class JoinerTest {

    private ArrayList<JoinableString> leftIterable;
    private ArrayList<JoinableString> rightIterable;
    private Joiner<JoinableString, JoinableString> joiner;

    @Before
    public void setup() {
        leftIterable = new ArrayList<>();
        rightIterable = new ArrayList<>();
    }

    private void createJoiner() {
        joiner = new Joiner<>(leftIterable.iterator(), rightIterable.iterator());
    }
    
    @Test
    public void noItems() {
        createJoiner();
        
        assertThat(joiner.hasNext()).isFalse();
        assertThat(joiner.nextJoined()).isNull();
    }

    @Test
    public void leftItemsOnly() {
        leftIterable.add(JoinableString.of("sbrubbles"));
        leftIterable.add(JoinableString.of("sbrubbles2"));
        
        createJoiner();
        
        assertThat(joiner.hasNext()).isTrue();
        
        assertJoined(joiner.nextJoined(), "sbrubbles", null);
        assertJoined(joiner.nextJoined(), "sbrubbles2", null);
        
        assertThat(joiner.hasNext()).isFalse();
    }
    
    @Test
    public void rightItemsOnly() {
        rightIterable.add(JoinableString.of("sbrubbles"));
        rightIterable.add(JoinableString.of("sbrubbles2"));
        
        createJoiner();
        
        assertThat(joiner.hasNext()).isTrue();
        
        assertJoined(joiner.nextJoined(), null, "sbrubbles");
        assertJoined(joiner.nextJoined(), null, "sbrubbles2");
        
        assertThat(joiner.hasNext()).isFalse();
    }
    
    @Test
    public void joinedItems() {
        leftIterable.add(JoinableString.of("sbrubbles"));
        
        rightIterable.add(JoinableString.of("sbrubbles"));
        
        createJoiner();
        
        assertThat(joiner.hasNext()).isTrue();

        assertJoined(joiner.nextJoined(), "sbrubbles", "sbrubbles");
        
        assertThat(joiner.hasNext()).isFalse();
    }
    
    @Test
    public void distinctItems() {
        leftIterable.add(JoinableString.of("aaa"));
        
        rightIterable.add(JoinableString.of("bbb"));
        
        createJoiner();
        
        assertJoined(joiner.nextJoined(), "aaa", null);
        assertJoined(joiner.nextJoined(), null, "bbb");
        
        assertThat(joiner.hasNext()).isFalse();
    }
    
    @Test
    public void mixedItems() {
        leftIterable.add(JoinableString.of("aaa"));
        leftIterable.add(JoinableString.of("bbb"));
        leftIterable.add(JoinableString.of("ddd"));
        
        rightIterable.add(JoinableString.of("aaa"));
        rightIterable.add(JoinableString.of("ccc"));
        rightIterable.add(JoinableString.of("ddd"));
        
        createJoiner();
        
        assertJoined(joiner.nextJoined(), "aaa", "aaa");
        assertJoined(joiner.nextJoined(), "bbb", null);
        assertJoined(joiner.nextJoined(), null, "ccc");
        assertJoined(joiner.nextJoined(), "ddd", "ddd");

        assertThat(joiner.hasNext()).isFalse();
    }
    
    private void assertJoined(Joined<JoinableString, JoinableString> joined,
                                String expectedLeft, String expectedRight) {
        if (expectedLeft == null)
            assertThat(joined.leftItem).isNull();
        else
            assertThat(joined.leftItem.value).isEqualTo(expectedLeft);
        
        if (expectedRight == null)
            assertThat(joined.rightItem).isNull();
        else
            assertThat(joined.rightItem.value).isEqualTo(expectedRight);
    }
    
}
