import java.lang.reflect.Method;

public class Pilha <X> implements Cloneable
{
    private Object[] elemento;   // Vetor da Pilha
    private int      ultimo=-1;  // Representa o valor vazio
    private int      capacidade; // Quantidade que cabe no vetor

    // Construtor
    public Pilha (int tamanho) throws Exception
    {
        if (tamanho <=0 )
            throw new Exception ("Tamanho invalido");

        this.elemento   = new Object[tamanho]; // O tamanho da Pilha, depende de como ela é instaciada dentro da classe
        this.capacidade = tamanho;             // A capacidade também irá representar seu tamanho
    }

    private void redimensioneSe(float porct)
    {
        // Instancia um novo vetor ('novo') e define seu tamanho como o parâmetro do método multiplicado pelo tamanho do vetor da Pilha (Esse valor é arredondado utilizando o Math.ceil())
        Object[] novo = new Object[(int)Math.ceil(this.elemento.length*porct)];

        // Percorre o vetor da Pilha aumentando seu valor, ao atribuir o novo vetor ao que já existe
        for(int i = 0 ; i<this.elemento.length ; i++ )
        {
            novo[i]= this.elemento[i] ;
        }

        // O vetor volta a ser o original com o tamanho do Novo vetor
        this.elemento = novo;
    }

    // Método para guardar algo na Pilha que recebe como parâmetro um valor genérico
    public void guardeUmItem (X x) throws Exception
    {
        // Se o parâmetro não existir, aciona uma exceção
        if (x==null)
            throw new Exception ("Falta o que guardar");

        // Se a Pilha estiver cheia, o método redmensioneSe é chamado, e o valor da Pilha é multiplicado para o dobro
        if (this.isCheia())
            this.redimensioneSe (2.0F);

        // Se ainda houver espaço, é "aberto" um indexador para se guardar o valor
        this.ultimo++;

        // Se o parâmetro passado é clonavel, o ultimo indexador que está "aberto" é guardado o valor CLONADO dele, senão simplesmente guarda-se o valor
        if (x instanceof Cloneable)
            this.elemento[this.ultimo] = meuCloneDeX(x);
        else
            this.elemento[this.ultimo] = x;
    }


    public X recupereUmItem () throws Exception
    {
        if (this.ultimo==-1)
            throw new Exception ("Nada a recuperar");

        X ret=null;
        if (this.elemento[this.ultimo] instanceof Cloneable)
            ret = meuCloneDeX((X)this.elemento[this.ultimo]);
        else
            ret = (X)this.elemento[this.ultimo];

        return ret;
    }

    public void removaUmItem () throws Exception
    {
        if (this.ultimo==-1)
            throw new Exception ("Nada a remover");

        this.elemento[this.ultimo] = null;
        this.ultimo--;

        if (this.elemento.length>this.capacidadeInicial && this.ultimo<=Math.round(this.elemento.length*0.25F))
            this.redimensioneSe (0.5F);
    }

    public boolean isCheia ()
    {
        if(this.ultimo+1==this.elemento.length)
            return true;

        return false;
    }

    public boolean isVazia ()
    {
        if(this.ultimo==-1)
            return true;

        return false;
    }

    private X meuCloneDeX(X x)
    {
        X ret  = null;

        try
        {
			Class<?> classe = x.getClass();
			Class<?>[] tipoDosParms = null;
			Method metodo = classe.getMethod("clone", tipoDosParms);
			Object[] parms = null;
			ret = (X)metodo.invoke(x, parms);
        }
        catch(Exception erro)
        {}

        return ret;
    }

    @Override
    public String toString()
    {
        String ret = this.ultimo + " elementos";

        if (this.ultimo!=-1)
            ret += ", sendo o ultimo "+this.elemento[this.ultimo];

        return ret;
    }

    @Override
    public boolean equals (Object obj)
    {
        if(this==obj)
            return true;

        if(obj==null)
            return false;

        if(this.getClass()!=obj.getClass())
            return false;

        Pilha<X> pil = (Pilha<X>) obj;

        if(this.ultimo!=pil.ultimo)
            return false;

        for(int i=0 ; i<this.ultimo;i++)
            if(!this.elemento[i].equals (pil.elemento[i]))
                return false;

        return true;
    }

    @Override
    public int hashCode ()
    {
        int ret=666/*qualquer positivo*/;

        //ret = ret*7/*primo*/ + new Integer(this.ultimo).hashCode();
        ret = ret*7/*primo*/ + Integer.valueOf(this.ultimo).hashCode();

        for (int i=0; i<this.ultimo; i++)
            ret = ret*7/*primo*/ + this.elemento[i].hashCode();

        if (ret<0)
            ret=-ret;

        return ret;
    }

    public Pilha(Pilha<X> modelo) throws Exception
    {
        if(modelo == null)
            throw new Exception("modelo ausente");

        this.ultimo = modelo.ultimo;

        this.elemento = new Object[modelo.elemento.length];

        for(int i=0 ; i<modelo.elemento.length ; i++)
            this.elemento[i] = modelo.elemento[i];
    }

    @Override
    public Object clone()
    {
        Pilha<X> ret = null;

        try
        {
            ret  = new Pilha(this);
        }
        catch(Exception erro)
        {}

        return ret;
    }
}