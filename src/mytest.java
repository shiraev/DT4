import java.util.ArrayList;

/**
 * Created by shira on 03/06/2017.
 */
public class mytest {
    public static void main(String[] args){
        BTree tree = new BTree(3);
        ArrayList<Block> blocks = Block.blockFactory(11, 20);
        for (int i=0; i<blocks.size(); i++){
            tree.insert(blocks.get(i));
        }

        BTreeLatex ev = new BTreeLatex(tree, "maayan");
        ev.addTreeState("insert1");
        tree.delete(12);
        ev.addTreeState("delete1");
        tree.delete(11);
        ev.addTreeState("delete2");

        ev.commitBufferedStates();
        ev.finish();
    }
}
