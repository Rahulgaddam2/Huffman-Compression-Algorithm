import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Implementation of Huffman Coding for data compression.
 * This class provides methods to encode and decode text using the Huffman algorithm.
 */
public class HuffmanCode {
    // Maps for encoding and decoding
    private HashMap<Character, String> encoder; // This map will store the encoding for each character.
    private HashMap<String, Character> decoder; // This map will help decode binary strings back into characters.

    /**
     * A private class to represent nodes of the Huffman tree.
     */
    private class Node {
        Character data; // Here, I store the character represented by this node (null for internal nodes).
        int cost; // Here, I store the frequency of the character.
        Node left; // I create a left child for this node.
        Node right; // I create a right child for this node.

        // Constructor to initialize the node with data and cost
        public Node(Character data, int cost) {
            this.data = data; // Assign the character data.
            this.cost = cost; // Assign the frequency of the character.
            this.left = null; // Initialize the left child as null.
            this.right = null; // Initialize the right child as null.
        }
    }

    /**
     * Constructs a Huffman tree and initializes encoder and decoder maps.
     *
     * @param feeder The input string to build the Huffman tree and frequency map.
     */
    public HuffmanCode(String feeder) {
        // Step 1: Let's create a frequency map of all characters in the input string.
        HashMap<Character, Integer> frequencyMap = new HashMap<>();
        for (int i = 0; i < feeder.length(); i++) {
            char cc = feeder.charAt(i); // Take one character at a time from the string.
            // Here, I count how often this character appears in the string.
            frequencyMap.put(cc, frequencyMap.getOrDefault(cc, 0) + 1);
        }

        // Step 2: Now, I create a priority queue (min-heap) to store nodes of the Huffman tree.
        PriorityQueue<Node> minHeap = new PriorityQueue<>((a, b) -> a.cost - b.cost);

        // I take the frequency map and convert its entries into nodes.
        Set<Map.Entry<Character, Integer>> entrySet = frequencyMap.entrySet();
        for (Map.Entry<Character, Integer> entry : entrySet) {
            Node node = new Node(entry.getKey(), entry.getValue()); // Create a node for each character and its frequency.
            minHeap.add(node); // Add the node to the min-heap.
        }

        // Step 3: Now I build the Huffman tree using the priority queue.
        while (minHeap.size() > 1) {
            // I take out the two nodes with the smallest frequencies.
            Node first = minHeap.poll(); // This is the node with the smallest frequency.
            Node second = minHeap.poll(); // This is the node with the second smallest frequency.

            // I create a new internal node. This node doesn't represent any character
            // but combines the frequency of the two nodes.
            Node newNode = new Node(null, first.cost + second.cost); 
            newNode.left = first; // I assign the first node as the left child.
            newNode.right = second; // I assign the second node as the right child.

            // Then, I add this new internal node back into the priority queue.
            minHeap.add(newNode);
        }

        // After the loop, the only remaining node in the queue is the root of the Huffman tree.
        Node fullTree = minHeap.poll();

        // I initialize the encoder and decoder maps to store the binary encodings and their reverse mappings.
        this.encoder = new HashMap<>();
        this.decoder = new HashMap<>();

        // Step 4: Let's populate the encoder and decoder maps by traversing the Huffman tree.
        this.initEncodeDecode(fullTree, "");
    }

    /**
     * Recursively populates the encoder and decoder maps by traversing the Huffman tree.
     *
     * @param node The current node in the Huffman tree.
     * @param osf  The binary string formed so far (path from the root to the current node).
     */
    private void initEncodeDecode(Node node, String osf) {
        if (node == null) {
            return; // If the node is null, I stop here.
        }

        // If the current node is a leaf node (it contains a character),
        // I store the binary string in the encoder and decoder maps.
        if (node.left == null && node.right == null) {
            this.encoder.put(node.data, osf); // Add this character and its binary encoding to the encoder map.
            this.decoder.put(osf, node.data); // Add the reverse mapping to the decoder map.
            return; // Done processing this leaf node.
        }

        // If it's not a leaf node, I recursively process its children.
        initEncodeDecode(node.left, osf + '0'); // Go left and add '0' to the binary string.
        initEncodeDecode(node.right, osf + '1'); // Go right and add '1' to the binary string.
    }

    /**
     * Encodes the input string into a binary string using the encoder map.
     *
     * @param source The input string to encode.
     * @return The encoded binary string.
     */
    public String encode(String source) {
        StringBuilder encodedString = new StringBuilder();

        // For each character in the source string, I get its binary encoding from the encoder map.
        for (int i = 0; i < source.length(); i++) {
            char cc = source.charAt(i);
            encodedString.append(this.encoder.get(cc)); // Append the binary encoding.
        }

        return encodedString.toString(); // Return the final encoded string.
    }

    
    public String decode(String source) {
        StringBuilder decodedString = new StringBuilder();
        StringBuilder key = new StringBuilder();

        // I go through the binary string one bit at a time.
        for (int i = 0; i < source.length(); i++) {
            key.append(source.charAt(i)); // Build the binary string key bit by bit.

            // When the key matches an entry in the decoder map, I decode it to a character.
            if (this.decoder.containsKey(key.toString())) {
                decodedString.append(this.decoder.get(key.toString())); // Append the decoded character.
                key.setLength(0); // Reset the key for the next character.
            }
        }

        return decodedString.toString(); // Return the final decoded string.
    }


    public static void main(String[] args) {
        // Input text to be encoded and decoded
        String text = "huffman coding algorithm";

        // I create a HuffmanCode object to process the input text.
        HuffmanCode huffman = new HuffmanCode(text);

        // I encode the input text into its binary representation.
        String encoded = huffman.encode(text);
        System.out.println("Encoded: " + encoded);

        // I decode the binary representation back into the original text.
        String decoded = huffman.decode(encoded);
        System.out.println("Decoded: " + decoded);
    }
}
