# MicroJava Compiler

Features:
 - Global variables
 - Local variables
 - Functions
 - Conditions
 - While and For loops
 - Foreach for iterating arrays
 
## Example of a program

```java
program example

const int one = 1;

int i;
bool ibool;

int arr2;

int arr[];
int a, curr;

{
	
	int sum(int z, int d)
	{
		return z + d;
	}
	

	int increment(int a, int inc) 
		int tmp; 
	{
		if(inc == one) tmp = 1;
		else if(inc == 2) tmp = a;

		return sum(a,tmp);
	}
	
	bool verify() {
		print('v');
		return false;
	}	
	
	void main()
	{
		
		if(1!=1) print(-6);
		else print(0);  
			
		ibool = false;
		i = 0;
		while(i < 5){
			if(i == 2) ibool = true;
			i++;
		}
		
		if(ibool)
			if(i == 5) print(1);
			else print(-1); 	
		a = 2;	
		arr = new int[3];
		arr[0] = 1 * a;
		arr[1] = 2 * a;
		arr[2] = sum(arr[1], arr[0]) * a + 1;
		if(verify() || arr[2] == 4) print(ord('A')); 
		else print(ord('a'));
		
		print(eol);
		i = 0;
		while (i < 10) {
			if(i == 3)
			{
				i++;
				continue;
			}
			if(i == 4) break;
			i = increment(i, 1);
		}
		
		if(i == 3 && verify()) print(-4);
		else print(4);  
		
		read(arr[0]);
		read(arr[1]);
		read(arr[2]);
		print(eol);
		print(sum(sum(arr[0], arr[1]), arr[2])); 
			
		print(eol); 
		print(increment(arr[2], 2));
		
		a = 3;
		i = 0;
		while (i < 3){
			arr[i] = arr[i] * 2 * a;
			i++;
		}
		
		arr.foreach(curr => print(curr););
		print(eol);
		
		arr2 = 0;
		arr.foreach(curr => {
			arr2 = arr2 + curr * curr;
		});
		print(arr2);
	
	}
}
```
