package com.example.saksham.blackoutrestuarant.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.saksham.blackoutrestuarant.R;
import com.example.saksham.blackoutrestuarant.database.Database;
import com.example.saksham.blackoutrestuarant.design.Cart;
import com.example.saksham.blackoutrestuarant.item.CartDetail;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.saksham.blackoutrestuarant.design.Cart.cart;
import static java.util.Locale.US;

/**
 * Created by saksham_ on 08-Apr-18.
 */

class CartViewHolder extends RecyclerView.ViewHolder{

    public TextView text_cart_name,txt_price,text_cart_quantity;
    public ImageView img_cart_count;
    public Button button;

    //ButtonFloatSmall button;


    public CartViewHolder(View itemView)
    {
        super(itemView);
        text_cart_name=(TextView)itemView.findViewById(R.id.cart_item_name);
        txt_price=(TextView)itemView.findViewById(R.id.cart_item_Price);
        //img_cart_count=(ImageView)itemView.findViewById(R.id.cart_item_count);
        text_cart_quantity=(TextView)itemView.findViewById(R.id.cart_item_Quantity);
        button=(Button) itemView.findViewById(R.id.cancel_cart_item);
    }

}

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private List<CartDetail> listData=new ArrayList<>();
    private Context context;


    public CartAdapter(List<CartDetail> listData, Context context)
    {
        this.listData=listData;
        this.context=context;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater =LayoutInflater.from(context);
        Log.i("cms","gello");
        View itemView = inflater.inflate(R.layout.cart_layout,parent,false);
        return new CartViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, final int position) {

        final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:

                        String name=listData.get(position).getProductName();
                        String size=listData.get(position).getSize();
                        new Database(context).deleteItem(name,size);
                        cart=new Database(context).getCart();
                        Cart.cartAdapter=new CartAdapter(cart,context);
                        Cart.loadListFood();

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        dialog.dismiss();
                        break;
                }
            }
        };

        //TextDrawable drawable=TextDrawable.builder().buildRound(""+listData.get(position).getQuantity(), Color.RED);
        //holder.img_cart_count.setImageDrawable(drawable);
        Locale locale =new Locale("en","IN");
        NumberFormat fmt=NumberFormat.getCurrencyInstance(locale);
        double price =((listData.get(position).getPrice())*(listData.get(position).getQuantity()));
        holder.txt_price.setText("Price: "+fmt.format(price)+" ("+listData.get(position).getPrice()+"*"+listData.get(position).getQuantity()+")");
        holder.text_cart_name.setText(listData.get(position).getProductName()+" ("+listData.get(position).getSize()+")");
        holder.text_cart_quantity.setText("Quantity: "+listData.get(position).getQuantity()+"");

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("REMOVE SELECTED ITEM FROM CART?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


}
