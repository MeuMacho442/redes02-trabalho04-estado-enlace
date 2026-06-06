package Model;

public class ListaDupla<T> {
    
    private Element tail = null, head = null;
    private int comprimento = 0;

    @SuppressWarnings("unchecked")
    public class Element {
          private T data;
          private Element prev, next;

          public Element(Element prev, T data, Element next) {
                 this.prev = prev; 
                 this.next = next;
                 this.data = data;
          }     

          public Element getNext() {
                 return  next;
          }
          
          public Element getPrev() {
                 return  prev;
          }

          public T getValor() {
                 return this.data;
          } 
    }

   public void inserirInicio(T item) {
          Element ptr = new Element(null, item, head);
          if(head == null) {
              tail = ptr; 
          } else {
              head.prev = ptr;
          }
          head = ptr;
          comprimento++;
   }

   public void inserirFim(T item) {
            Element ptr = new Element(tail, item, null);
            if(head == null) {
               head = ptr;
            } else {
               tail.next = ptr;
            } 
           tail = ptr;
           comprimento++; 
   }
   
   public T retirar() {
        if(head == null) {
            return null;
        }
        T valor = head.data;
        head = head.next; 
        return valor;
   }

   public T getHeadValue() {
        if(head == null) {
            return null;
        }
        T valor = head.data; 
        return valor;
   }

   public String getPrimeiro() {
            if(head == null) {
              throw new RuntimeException("lista vazia");
            }
          return "" + head.data;
   }
  
   public String getUltimo() {
          if(tail == null) {
             throw new RuntimeException("Lista vazia");
          }
          return "" + tail.data;
   }

   public void remover(T item) {
           Element ptr = head;
           
           while(ptr != null && !ptr.data.equals(item)) {
                 ptr = ptr.next;
                 
           }
           
           if(ptr == null) {
             throw new RuntimeException("Valor nao encontrado.");
           }
            
           if(ptr == head && ptr.next != null) {
               head = ptr.next;
              // System.out.println("0" + ptr.next); 
               ptr.next.prev = null;
           } else if(ptr == head) { 
               head = ptr.next;
           } else if(ptr == tail) {
               tail = ptr.prev;
             //  System.out.println("1"+ptr.next); 
               ptr.prev.next = null;
           } else
           { 
             // System.out.println("2" + ptr.prev);
              ptr.prev.next = ptr.next;
            //  System.out.println("3" + ptr.next); 
              ptr.next.prev = ptr.prev;
           }
   }

  public void removerFim() {
         tail = tail.prev;
       if(tail != null) {
         tail.next = null;
       } else {
          head = null;
       } 
  }

  public void limpa() {
         this.head = null;
         this.tail = null;  
  }

  public void inverter() {
         Element ptr, R;
         ptr = head;
         head = tail;
         tail = ptr;

         do {
             R = ptr.next;
             ptr.next = ptr.prev;  
             ptr.prev = R;
             ptr = ptr.prev;
         }while(ptr != null);
  }

  public String ExibirLista() {
            Element ptr = head;
            String lista = "";
            while(ptr != null) {
                lista += ptr.data + " "; 
                ptr = ptr.next;
            }

            return lista + "\n";
  }

  

  public int getComprimentoLista() {
         return comprimento; 
  } 

  public Element getHead() {
         return head;
  }
 
  public Element getTail() {
         return tail;
  }
} 
