
import com.google.gson.Gson; // my personal favourite
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.*;

public class Main {
    private static  Map<Integer, Node> nodes;
    private static  Node root;

    public static void main(String[] args){
        nodes = new HashMap<>();
        try {
            String data = readFromFile("src/nodeExplorer.json");

            Type targetClassType = new TypeToken<ArrayList<Node>>() {}.getType();

            ArrayList<Node> arrayNodes = new Gson().fromJson(data, targetClassType);

            printTree(arrayNodes);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void createHashMap(List<Node> arrayNodes) {
       for(Node x: arrayNodes){
           nodes.put(x.id, x);
       }
       // Add a dummy top level node so as to maintain the tree structure
        // We won't display this
       root = new Node(){{id = 0; parentID = -1;}};
       nodes.put(-1 ,root);
    }


    private static List<Node> getChildElements(int id){
        List<Node> children = new ArrayList<>();
        for (Node n: nodes.values()){
            if(n.parentID == id)children.add(n);
        }
        return children;
    }


    private static  void recursiveTreePrint(Node localRoot, int indentation){
        if (localRoot.id != 0) {
            for (int i = 0; i < indentation; i++) {
                System.out.print("\t");
            }
            System.out.println(localRoot.label);
        }

        List<Node> children = getChildElements(localRoot.id);
        for(Node n: children){
            recursiveTreePrint(n, indentation + 1);
        }
    }

    private static String readFromFile(String filename) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(filename));
        String content = sc.useDelimiter("\\Z").next();
        sc.close();
        return content;
    }

    private static void printTree(ArrayList<Node> nodes) {
        createHashMap(nodes);
        recursiveTreePrint(root, 0);
    }

    static class Node {
        int id;
        int parentID;
        String label;
    }

}
