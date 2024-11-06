public class Bomba extends Elemento implements Destruible{
    private int radio;

    public Bomba(Escenario e, Posicion p, int r){
        super(e, p);
        this.radio = r;
    }

    public void explotar(){
        System.out.println("Explotando bomba");
        escenario.destruirElementos(this.posicion, this.radio);
    }

    public int getRadio(){
        return radio;
    }

    @Override
    public String destruir(){
        return "Esplotando Bomba!!";
    }
}
