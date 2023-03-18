package com.example.eatsimpleproj.Adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eatsimpleproj.CartListActivity;
import com.example.eatsimpleproj.Domain.FoodDomain;
import com.example.eatsimpleproj.Interface.ChangeItemListener;
import com.example.eatsimpleproj.R;
import com.example.eatsimpleproj.ShowDetailActivity;
import com.example.eatsimpleproj.helper.ManagementCart;

import java.util.ArrayList;


public class CartListAdaptor extends RecyclerView.Adapter<CartListAdaptor.ViewHolder>{
    private ArrayList<FoodDomain> foodDomains;
    private ManagementCart managementCart;
    private ChangeItemListener changeItemListener;

    public CartListAdaptor(ArrayList<FoodDomain> foodDomains, Context context, ChangeItemListener changeItemListener) {
        this.foodDomains = foodDomains;
        this.managementCart = new ManagementCart(context);
        this.changeItemListener = changeItemListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate=LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListAdaptor.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.title.setText(foodDomains.get(position).getTitle());
        holder.feeEachItem.setText(String.valueOf(foodDomains.get(position).getFee()));
        holder.totalEachItem.setText(String.valueOf(Math.round(foodDomains.get(position).getNumberInCart()* foodDomains.get(position).getFee())*100/100));
        holder.num.setText(String.valueOf(foodDomains.get(position).getNumberInCart()));

        int drawableResourceId=holder.itemView.getContext().getResources().getIdentifier(foodDomains.get(position).getPic(),"drawable",holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.pic);

        holder.addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                managementCart.plusNumberFood(foodDomains, position, new ChangeItemListener() {
                    @Override
                    public void changed(){
                        notifyDataSetChanged();
                        changeItemListener.changed();

                    }
                });
            }
        });
        holder.minusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                managementCart.minusNumberFood(foodDomains, position, new ChangeItemListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeItemListener.changed();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,feeEachItem;
        ImageView pic,addItem,minusItem;
        TextView totalEachItem,num;


        public ViewHolder(View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.title);
            feeEachItem=itemView.findViewById(R.id.feeEachItem);
            pic=itemView.findViewById(R.id.picCart);
            totalEachItem=itemView.findViewById(R.id.feeTxt);
            num=itemView.findViewById(R.id.numberItemTxt);
            addItem=itemView.findViewById(R.id.plusCartBtn);
            minusItem=itemView.findViewById(R.id.minusCartBtn);
        }
    }
}

