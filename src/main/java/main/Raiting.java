package main;

/**
 * @author Y.Nevenchenov
 *
 *
 *
 * Created on 03-17.
 */
public final class Raiting {

final static Raiting LUX = new Raiting("люкс");
final static Raiting CONVENIENCED = new Raiting("с удобствами");
final static Raiting ECONOM = new Raiting("эконом");


private String raiting;

private Raiting(String raiting)
{
    this.raiting = raiting;
}

@Override
public String toString()
{
    return this.raiting;
}
}
