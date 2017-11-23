package joiner;

import java.util.Iterator;

public class Joiner {

    private Iterator<String> leftIterator;
    private Iterator<String> rightIterator;

    private String leftItem;
    private String rightItem;
    
    public Joiner(Iterator<String> leftIterator, Iterator<String> rightIterator) {
        this.leftIterator = leftIterator;
        this.rightIterator = rightIterator;
    }

    public Joined nextJoined() {
        if (!hasNext())
            return null;
        
        if (leftItem == null)
            readNextLeftIfAvailable();
        
        if (rightItem == null)
            readNextRightIfAvailable(); 
        
        return buildJoined();        
    }

    private Joined buildJoined() {
        Joined joined = new Joined();
        
        if (rightItem == null) {
            consumeLeft(joined);
        } else if (leftItem == null) {
            consumeRight(joined);
        } else if (leftItem.equals(rightItem)) {
            consumeLeft(joined);
            consumeRight(joined);
        } else if (leftItem.compareTo(rightItem) > 0) {
            consumeRight(joined);
        } else {
            consumeLeft(joined);
        }
        
        return joined;
    }

    private void consumeLeft(Joined joined) {
        joined.leftItem = leftItem;
        leftItem = null;
    }
    
    private void consumeRight(Joined joined) {
        joined.rightItem = rightItem;
        rightItem = null;
    }

    private void readNextRightIfAvailable() {
        if (rightIterator != null && rightIterator.hasNext())
            rightItem = rightIterator.next();
    }

    private void readNextLeftIfAvailable() {
        if (leftIterator != null && leftIterator.hasNext())
            leftItem = leftIterator.next();
    }

    public boolean hasNext() {
        return leftItem != null || 
               rightItem != null ||  
               leftIterator != null && leftIterator.hasNext() || 
               rightIterator != null && rightIterator.hasNext();
    }

    
}
