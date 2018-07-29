/******************************************************************************

                            Online C Compiler.
                Code, Compile, Run and Debug C program online.
Write your code in this editor and press "Run" button to compile and execute it.

http://www.ebyte.it/library/educards/constants/ConstantsOfPhysicsAndMath.html



1068077148 -> 1.3247179985e+00                                                                                                                                    
1805536512 -> 3.8274780344e+26                                                                                                                                    
1005526689 -> 7.2973524220e-03                                                                                                                                    
1727990831 -> 6.0221410038e+23                                                                                                                                    
1301214146 -> 2.9979244800e+08                                                                                                                                    
428181300 -> 1.3806485791e-23                                                                                                                                     
1107313295 -> 3.2064998627e+01                                                                                                                                    
-2147483648 -> -0.0000000000e+00                                                                                                                                  
993912976 -> 2.8977729380e-03                                                                                                                                     
778615823 -> 5.2917722287e-11                                                                                                                                     
1090848777 -> 8.3144617081e+00                                                                                                                                    
                                      

*******************************************************************************/
#include <stdio.h>
#include <stdint.h>


void convert(uint32_t number) {
    float * myfloat = (float*) &number;
    printf("%d -> %.10e \n", number, (float) *myfloat);
}
int main()
{
    convert(1068077148); 
    convert(1805536512); 
    convert(1005526689); 
    convert(1727990831); 
    convert(1301214146); 
    convert(428181300);  
    convert(1107313295); 
    convert(2147483648); 
    convert(993912976);  
    convert(778615823);  
    convert(1090848777);
    return 0;
}
