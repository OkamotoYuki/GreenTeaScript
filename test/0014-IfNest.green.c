static int f__A(int a0, int b1){
   int n2;
   {
      n2 = 1;
      if(a0 < 10) {
         if(b1 < 100) {
            n2 = n2 + 2;
         } else {
            n2 = n2 + 4;
         };
         n2 = n2 + 6;
      } else {
         if(b1 < 200) {
            n2 = n2 + 20;
         } else {
            n2 = n2 + 40;
         };
         n2 = n2 + 60;
      };
      n2 = n2 + 200;
      return n2;
   };
}
{
   f__A(20000,10000);
}