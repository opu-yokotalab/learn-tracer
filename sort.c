void swap(int *a, int *b){
  int c;
  c = *a; *a = *b; *b = c;
}

void insertion_sort(int *a, int n){
 	int i, j;

	for (i = 1; i < n; i++) {
	  j = i;
	  while ((j > 0) && (a[j] < a[j - 1])) {
	    swap(&a[j], &a[j - 1]);
	    j--;
	  }
 	}
} 
int main(){
 	int a[] = {5, 9, 1, 2, 0, 4, 3};
	int i,l=1;

 	insertion_sort(a, sizeof(a) / sizeof(int));

	printf("a[]=");	

	for(i=0;i<7;i++){
	  printf("%d,",a[i]);
	}
	printf("\n");
 	return 0;
}
