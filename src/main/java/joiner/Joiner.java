package joiner;

import java.util.Iterator;

public class Joiner<L extends Joinable, R extends Joinable> {

    private Iterator<L> leftIterator;
    private Iterator<R> rightIterator;

    private L leftItem;
    private R rightItem;
    
    public Joiner(Iterator<L> leftIterator, Iterator<R> rightIterator) {
        this.leftIterator = leftIterator;
        this.rightIterator = rightIterator;
    }

    public Joined<L, R> nextJoined() {
        if (!hasNext())
            return null;
        
        if (leftItem == null)
            readNextLeftIfAvailable();
        
        if (rightItem == null)
            readNextRightIfAvailable(); 
        
        return buildJoined();        
    }

    private Joined<L, R> buildJoined() {
        Joined<L, R> joined = new Joined<>();
        
        if (rightItem == null) {
            consumeLeft(joined);
        } else if (leftItem == null) {
            consumeRight(joined);
        } else if (leftItem.getJoinKey().equals(rightItem.getJoinKey())) {
            consumeLeft(joined);
            consumeRight(joined);
        } else if (leftItem.getJoinKey().compareTo(rightItem.getJoinKey()) > 0) {
            consumeRight(joined);
        } else {
            consumeLeft(joined);
        }
        
        return joined;
    }

    private void consumeLeft(Joined<L, R> joined) {
        joined.leftItem = leftItem;
        leftItem = null;
    }
    
    private void consumeRight(Joined<L, R> joined) {
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
