package com.yemen.oshopping.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import android.widget.*

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.squareup.picasso.Picasso
import com.yemen.oshopping.R
import com.yemen.oshopping.model.Cart
import com.yemen.oshopping.model.ProductItem
import com.yemen.oshopping.model.Report
import com.yemen.oshopping.model.Rating
import com.yemen.oshopping.viewmodel.OshoppingViewModel

private const val ARG_PARAM1 = "product_id"

class ProductDetailsFragment : Fragment() {
    lateinit var productImage: ImageView
    lateinit var productName: TextView
    lateinit var productVendor: TextView
    lateinit var rialProductPrice: TextView
    lateinit var dollarProductPrice: TextView
    lateinit var addToCart: Button
    lateinit var ratingBarTexView: TextView

    // lateinit var productQuantity: TextView
    //lateinit var productDiscount: TextView
    //lateinit var productDetails: TextView
    lateinit var productItem: ProductItem
    lateinit var ratingBar: RatingBar
    lateinit var ratingBar2: RatingBar
    lateinit var submitRatingBTN: Button


    var url: String = "http://192.168.1.4/oshopping_api/"
    private var param1: Int = 1
    lateinit var oshoppingViewModel: OshoppingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)

        }
        oshoppingViewModel = ViewModelProviders.of(this).get(OshoppingViewModel::class.java)
        oshoppingViewModel.getProductById(param1)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product_details, container, false)
        productImage = view.findViewById(R.id.product_img)
        productName = view.findViewById(R.id.product_name)
        productVendor = view.findViewById(R.id.product_vendor)
        rialProductPrice = view.findViewById(R.id.product_price_r)
        dollarProductPrice = view.findViewById(R.id.product_price_d)
        addToCart = view.findViewById(R.id.product_add_btn)

        addToCart.setOnClickListener {
            val cart= Cart(fk_user_id=1,fk_product_id =9,cart_statuse =0)
            oshoppingViewModel.pushCart(cart)
        }

        ratingBar = view.findViewById(R.id.rating_Bar_product_details)
        ratingBarTexView = view.findViewById(R.id.rating_bar_text_view_prodcut_details)
        ratingBar2 = view.findViewById(R.id.rating_Bar_2_product_details)
        submitRatingBTN = view.findViewById(R.id.submit_rating_button)
        if (ratingBar2 != null) {
            submitRatingBTN.setOnClickListener {
                val ratingBarValue = ratingBar2.rating.toString()
                Toast.makeText(
                    this@ProductDetailsFragment.context,
                    "Rating is: " + ratingBarValue, Toast.LENGTH_SHORT
                ).show()
                var rating = Rating(
                    product_id = productItem.product_id,
                    user_id = 7,
                    rating = ratingBar2.rating.toInt()
                )
                oshoppingViewModel.pushRating(rating)

            }
        }


        //productQuantity = view.findViewById(R.id.prodcut_quantity_text_view)
        //productDiscount = view.findViewById(R.id.prodcut_discount_text_view)
        // productDetails = view.findViewById(R.id.product_details_text_view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        oshoppingViewModel.productItemLiveDataByID.observe(
            viewLifecycleOwner,
            Observer { productDetails ->
                productDetails?.let {
                    Log.d("FromObserver", "$it")
                    this.productItem = productDetails[0]
                    updateUI()
                }
            })
    }

    fun updateUI() {
        productName.text = productItem.product_name
        rialProductPrice.text = productItem.yrial_price.toString() + "RY"
        dollarProductPrice.text = productItem.dollar_price.toString() + "$"
        ratingBar.rating = productItem.rating_average
        ratingBarTexView.text = productItem.number_of_ratings.toString() + " votes"
        //productQuantity.text = "Quantity: " + productItem.product_quantity.toString()
        //productDiscount.text = "discount: " + productItem.product_discount.toString()
        //productDetails.text = "product details: " + productItem.product_details
        var compositeProductUrl = url + productItem.product_img
        var conditionString = "string" + productItem.product_img
        if (!conditionString.equals("stringnull"))
            Picasso.get().load(compositeProductUrl).into(productImage)

    }

    companion object {
        fun newInstance() =
            ProductDetailsFragment()

        fun newInstance(param1: Int) =
            ProductDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }


}
