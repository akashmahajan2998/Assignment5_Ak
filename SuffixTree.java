import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

class SuffixNode{
    //String pat;
    int level;
    int id;
    int endid;
    SuffixNode next;
    SuffixNode child;
    int mark;
}

public class SuffixTree {
    SuffixNode root;
    ArrayList<Integer> arr = new ArrayList<>();

    SuffixNode newSuffixNode(String pat, int id) {
        SuffixNode n = new SuffixNode();
        //n.pat = pat;
        n.id = id;
        n.endid = id+pat.length()-1;
        n.mark = -1;
        return n;
    }

    void Insert(String s, int i, SuffixNode b, int j) {
        //SuffixNode b = root;

        boolean check = false;
        while(s.charAt(b.id)!=s.charAt(i)) {
            if(b.next==null) {
                check = true;
                b.next = newSuffixNode(s.substring(i), i);
                b.next.mark=i;
                break;
            }
            else{
                b=b.next;
            }
        }
        if (!check) {
//			System.out.println(b.id);
//			System.out.println("k"+b.id);
            int idx = b.id+1;
            i++;
            while(true) {
                if (idx<=b.endid && i<=s.length()-1 && s.charAt(idx)==s.charAt(i)) {
//					System.out.println(i);
                    idx++;
                    i++;
                }
                else if(idx<=b.endid && i<=s.length()-1 && s.charAt(idx)!=s.charAt(i)) {
                    int tem = b.endid;
                    b.endid=idx-1;
                    SuffixNode t = b.child;
                    b.child = newSuffixNode(s.substring(idx, tem+1), idx);
                    b.child.mark = b.mark;
                    b.child.child =t;
                    b.mark = -1;
                    b.child.next = newSuffixNode(s.substring(i), i);
                    b.child.next.mark =j;
                    break;
                }
                else if(idx==b.endid && i==s.length()-1) {
                    b.mark = j;
                    break;
                }
                else if(idx<=b.endid && i>s.length()-1) {
//					System.out.println("Hi");
                    int tem = b.endid;
                    b.endid=idx-1;
                    SuffixNode t = b.child;
                    b.child = newSuffixNode(s.substring(idx, tem+1), idx);
                    b.child.mark = b.mark;
                    b.child.child =t;
                    //System.out.println(j);
                    b.mark = j;
                    break;
                }
                else if(idx==b.endid) {
                    Insert(s, i, b.child, j);
                    break;
                }
            }
        }
    }
    public void check(SuffixNode a) {
        if(a!=null) {
            //System.out.println(a.id+" "+a.endid);
            if(a.mark!=-1)
                //System.out.println(a.mark);
                arr.add(a.mark);
            if(a.child!=null) {
                a=a.child;
                while(a!=null) {
                    check(a);
                    a=a.next;
                }
            }
        }
    }

    void Search(String pat, int i, String s, SuffixNode b, boolean que){
        boolean check = false;
        if(que) {
            while (s.charAt(b.id) != pat.charAt(i)) {
                if (b.next == null) {
                    check = true;
//                b.next = newSuffixNode(s.substring(i), i);
//                b.next.mark=i;
                    break;
                } else {
                    b = b.next;
                }
            }
        }
        if (pat.charAt(i)=='?'){
            while (b != null) {
                Search(pat, i+1, s, b, false);
                b=b.next;
            }

        }

        else if (!check) {
//			System.out.println(b.id);
//			System.out.println("k"+b.id);
            int idx = b.id+1;
            i++;
            while(true) {
                if ( i<=pat.length()-1 && pat.charAt(i)=='?'&& idx<=b.endid){
                    idx++;
                    i++;
                }
                else if (idx<=b.endid && i<=pat.length()-1 && s.charAt(idx)==pat.charAt(i)) {
//					System.out.println(i);
                    idx++;
                    i++;
                }
                else if(idx<=b.endid && i<=pat.length()-1 && s.charAt(idx)!=pat.charAt(i)) {
                    System.out.println("NoT ExisT");
                    break;
                }
                else if(i>pat.length()-1) {
                    check(b);
                    break;
                }
                else if(idx>b.endid) {
                    Search(pat, i, s, b.child, true);
                    break;
                }
            }
        }
        else{
//            System.out.println("NOt ExiST");
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        SuffixTree st = new SuffixTree();
        try  {
            BufferedReader bu = new BufferedReader(new FileReader(args[0]));
//            Scanner kb=new Scanner(new File(args[0]));
            String s = bu.readLine();
            //String s = "banana";
            String line2 = bu.readLine();
            int n = Integer.parseInt(line2);
            st.root = st.newSuffixNode(s, 0);
            st.root.mark = 0;
            for (int i = 1; i < s.length(); i++)
                st.Insert(s, i, st.root, i);
            //		System.out.println(st.root.mark);
            SuffixNode b = st.root;
            //        while(b!=null) {
            //            st.check(b);
            //            System.out.println("KKK");
            //            b=b.next;
            //        }
            int j=1;
            BufferedWriter f=new BufferedWriter(new FileWriter(args[1]));
            while (j<=n) {
                j++;
                String v = bu.readLine();
                st.Search(v, 0, s, st.root, true);
                for (int i = 0; i < st.arr.size(); i++) {
                    System.out.println(st.arr.get(i));
                    f.write(st.arr.get(i)+" "+(st.arr.get(i)+v.length()-1));
                    f.newLine();
                }
            }
            f.close();
        } catch (IOException e) {
            System.out.println("gjhg"+e);
            return;
        }
    }
}
