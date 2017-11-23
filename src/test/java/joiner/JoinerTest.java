package joiner;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class JoinerTest {

    private ArrayList<String> leftIterable;
    private ArrayList<String> rightIterable;

    @Before
    public void setup() {
        leftIterable = new ArrayList<>();
        rightIterable = new ArrayList<>();
    }

    @Test
    public void noItems() {
        Joiner joiner = new Joiner(leftIterable.iterator(), rightIterable.iterator());
        
        assertThat(joiner.hasNext()).isFalse();
        assertThat(joiner.nextJoined()).isNull();
    }

    @Test
    public void leftItemsOnly() {
        leftIterable.add("sbrubbles");
        leftIterable.add("sbrubbles2");
        
        Joiner joiner = new Joiner(leftIterable.iterator(), rightIterable.iterator());
        
        assertThat(joiner.hasNext()).isTrue();
        
        assertJoined(joiner.nextJoined(), "sbrubbles", null);
        assertJoined(joiner.nextJoined(), "sbrubbles2", null);
        
        assertThat(joiner.hasNext()).isFalse();
    }
    
    @Test
    public void rightItemsOnly() {
        rightIterable.add("sbrubbles");
        rightIterable.add("sbrubbles2");
        
        Joiner joiner = new Joiner(leftIterable.iterator(), rightIterable.iterator());
        
        assertThat(joiner.hasNext()).isTrue();
        
        assertJoined(joiner.nextJoined(), null, "sbrubbles");
        assertJoined(joiner.nextJoined(), null, "sbrubbles2");
        
        assertThat(joiner.hasNext()).isFalse();
    }
    
    @Test
    public void joinedItems() {
        leftIterable.add("sbrubbles");
        
        rightIterable.add("sbrubbles");
        
        Joiner joiner = new Joiner(leftIterable.iterator(), rightIterable.iterator());
        
        assertThat(joiner.hasNext()).isTrue();

        assertJoined(joiner.nextJoined(), "sbrubbles", "sbrubbles");
        
        assertThat(joiner.hasNext()).isFalse();
    }
    
    @Test
    public void distinctItems() {
        leftIterable.add("aaa");
        
        rightIterable.add("bbb");
        
        Joiner joiner = new Joiner(leftIterable.iterator(), rightIterable.iterator());
        
        assertJoined(joiner.nextJoined(), "aaa", null);
        assertJoined(joiner.nextJoined(), null, "bbb");
        
        assertThat(joiner.hasNext()).isFalse();
    }
    
    @Test
    public void mixedItems() {
        leftIterable.add("aaa");
        leftIterable.add("bbb");
        leftIterable.add("ddd");
        
        rightIterable.add("aaa");
        rightIterable.add("ccc");
        rightIterable.add("ddd");
        
        Joiner joiner = new Joiner(leftIterable.iterator(), rightIterable.iterator());
        
        assertJoined(joiner.nextJoined(), "aaa", "aaa");
        assertJoined(joiner.nextJoined(), "bbb", null);
        assertJoined(joiner.nextJoined(), null, "ccc");
        assertJoined(joiner.nextJoined(), "ddd", "ddd");

        assertThat(joiner.hasNext()).isFalse();
    }
    
    private void assertJoined(Joined joined, String expectedLeft, String expectedRight) {
        assertThat(joined.leftItem).isEqualTo(expectedLeft);
        assertThat(joined.rightItem).isEqualTo(expectedRight);
    }
    
}
