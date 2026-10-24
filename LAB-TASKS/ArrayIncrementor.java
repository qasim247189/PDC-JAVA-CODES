import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
public class ArrayIncrementor extends RecursiveAction {
private static final int THRESHOLD=10;
private int[] array;
private int start;
private int end;
public ArrayIncrementer(int[] array,int start,int end){
this.array=array;
this.start=start;
this.end=end;}
@Override
protected void compute(){
int size=end-start;
if(size<=THRESHOLD){
incrementDirectly();}
else{
int middle=start+(size/2);
ArrayIncrementer leftTask=new ArrayIncrementer(array,start,middle);
ArrayIncrementer rightTask=new ArrayIncrementer(array,middle,end);
invokeAll(leftTask,rightTask);}}
private void incrementDirectly(){
for(int i=start;i<end;i++){
array[i]=array[i]+1;}}
public static void main(String[] args){
int[] myArray={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
System.out.println("Original Array:");
for(int num:myArray){
System.out.print(num+" ");}
System.out.println();
ArrayIncrementer mainTask=new ArrayIncrementer(myArray,0,myArray.length);
ForkJoinPool pool=new ForkJoinPool();
try{
pool.invoke(mainTask);
}finally{
pool.shutdown();}
int sum=0;
System.out.println("\nArray after incrementing by 1:");
for(int num:myArray){
System.out.print(num+" ");
sum+=num;}
System.out.println("\n\nTotal Sum of all elements: "+sum);}}