package nl.gokhankacan.androidsql;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	Button btnViewProducts;
	Button btnNewProduct;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Knoppen
		btnViewProducts = (Button) findViewById(R.id.btnViewProducts);
		btnNewProduct = (Button) findViewById(R.id.btnCreateProduct);
		
		btnViewProducts.setOnClickListener(new View.OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				// All Products Activity Start
				Intent i = new Intent(getApplicationContext(), AllProductsActivity.class);
				startActivity(i);
			}
		});
		
		btnNewProduct.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// New Producten Toevoegen
				Intent i = new Intent(getApplicationContext(), NewProductActivity.class);
				startActivity(i);
				
			}
		});
		
	} // onCreate-END
}
