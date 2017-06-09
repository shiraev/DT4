import java.util.ArrayList;

/**
 * Created by shira on 03/06/2017.
 */
public class mytest {
    public static void main(String[] args){
        BTree tree = new BTree(2);
        ArrayList<Block> blocks = Block.blockFactory(1, 9);
        for (Block b : blocks){
            tree.insert(b);
        }


        BTreeLatex ev = new BTreeLatex(tree, "maayan");
        ev.addTreeState("insert1");
        /*tree.delete(10);
        ev.addTreeState("insert2");
        tree.delete(19);
        ev.addTreeState("insertd");*/
        tree.delete(7);
        tree.delete(6);
        ev.addTreeState("insert1");
        ev.commitBufferedStates();
        ev.finish();
    }
}
