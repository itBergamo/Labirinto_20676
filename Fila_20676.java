import java.lang.reflect.Method;

public class Fila <X> implements Cloneable
{
    //private X[] elemento;
    private Object[] elemento;
    private int ultimo=-1; //vazio
    private int capacidadeInicial;

    public Fila (int tamanho) throws Exception
    {
        if (tamanho<=0)
            throw new Exception ("Tamanho invalido");

        //this.elemento=new X [tamanho];
        this.elemento=new Object [tamanho];
        this.capacidadeInicial=tamanho;
    }

    private void redimensioneSe(float porct)
    {
        Object[] novo = new Object[(int)Math.ceil(this.elemento.length*porct)];

        for(int i = 0 ; i<this.elemento.length ; i++ )
        {
            novo[i]= this.elemento[i] ;
        }

        this.elemento = novo;
    }

    public void guardeUmItem (X x) throws Exception
    {
        if (x==null)
            throw new Exception ("Falta o que guardar");

        if (this.isCheia())
            this.redimensioneSe (2.0F);

        this.ultimo++;

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
            ret = meuCloneDeX((X)this.elemento[1]);
        else
            ret = (X)this.elemento[1];

        return ret;
    }

    public void removaUmItem () throws Exception
    {
        if (this.ultimo==-1)
            throw new Exception ("Nada a remover");

        for (int i=0; i<this.ultimo; i++)
            this.elemento[i] = this.elemento[i+1];

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
			ret = (X)metodo.invoke(x,parms);
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

        Fila<X> fil = (Fila<X>) obj;

        if(this.ultimo!=fil.ultimo)
            return false;

        for(int i=0 ; i<this.ultimo;i++)
            if(!this.elemento[i].equals (fil.elemento[i]))
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

    public Fila(Fila<X> modelo) throws Exception
    {
        if(modelo == null)
            throw new Exception("modelo ausente");

        this.ultimo = modelo.ultimo;

        this.elemento = new Object[modelo.elemento.length];

        for(int i=0 ; i<=modelo.ultimo; i++)
            this.elemento[i] = modelo.elemento[i];
    }

    @Override
    public Object clone()
    {
        Fila<X> ret = null;

        try
        {
            ret  = new Fila(this);
        }
        catch(Exception erro)
        {}

        return ret;
    }
}