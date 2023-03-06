import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ValidBST {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {
        }
        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    AtomicBoolean result = new AtomicBoolean(true);
    List<Integer> visited = new ArrayList<>();
    List<Boolean> seen = new ArrayList<>();
    TreeNode previous;

    public boolean isValidBST(TreeNode root) {
        // inorder traversal. left. root. right
        if(root != null) {
            // traverse left
            if(root.left != null) {
                isValidBST(root.left);
            }
            if(previous != null) {
                if(previous.val >= root.val) {
                    System.out.println("Previous: "+ previous.val);
                    System.out.println("Root: "+ root.val);
                    result.set(false);
                }
            }
            ////////////////////////////////////
            // traverse right
            // store previous node first
            previous = root;
            if(root.right != null) {
                isValidBST(root.right);
            }
        }
        //System.out.println("Visited: "+visited);
        return result.get();
    }

    public static void main(String[] args) {
        ValidBST vbst = new ValidBST();
        TreeNode root = new TreeNode(5, null, null);
        root.left = new TreeNode(4, null, null);
        root.right = new TreeNode(6, new TreeNode(3), new TreeNode(7));
        System.out.println("Result: " + vbst.isValidBST(root));
    }
}
