


// each charecter in java has a size of 2 bytes
// example String name = "rahul" -> the size is 2*5 = 10 bytes.
// if you want in bits multipul the ans by 8 2*5*8 = 80 bits.


/*
 * Steps to compress an file (Huffman Algorithm);
 * 
 * step 1 - Pass the string (we need to create an constructer that takes input of one parameter)
 * step 2 - Make an frequency map of all charecters in the string and its frequency (HashMap<Charecter , String>)
 * step 3 - for every key in frequncy map create an node(node is an class)
 *          Node has char data , int frequency , node left , node right (Basically its an tree)
 * step 4 - after creating the nodes add all nodes to minheap - compare the freqency's and add in accending order
 * step 5 - remove two elements(first 2 elements) from heap and combine them and push back to minheap
 *          with this you will form an tree
 * step 6 - when you move left to the tree and 0 when you move right to the tree add 1
 */