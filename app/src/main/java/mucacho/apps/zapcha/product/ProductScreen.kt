package mucacho.apps.zapcha.product

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mucacho.apps.zapcha.R
import mucacho.apps.zapcha.domain.Product
import mucacho.apps.zapcha.product.ui.theme.ZapChaTheme

@Composable
fun ProductScreen(productViewModel: ProductViewModel) {
    val product by productViewModel.selectedProduct.observeAsState()

    val productEdited = productViewModel.productEdited

    product?.let {
        // If plant is not null, display the content
        Surface {
            Column(Modifier.padding(dimensionResource(R.dimen.spacing_normal))) {
                ProductName(productEdited,it.name, { productViewModel.newProductName(it) })
                ProductPrice(productEdited,it.price, { productViewModel.newPrice(it) })
                ProductStock(productEdited,it.stock, { productViewModel.newStockQty(it) })
                ProductDescription(productEdited,it.description, { productViewModel.newProductDescr(it) })
                SaveChanges({productViewModel.onSaveProduct() })
                SellBottle({ productViewModel.sellOneBottle() })
            }
        }
    }
}

@Composable
private fun ProductName(productEdited: Boolean, productName: String, onNewName: (String) -> Unit) {
    OutlinedTextField(
        value = productName,
        onValueChange = onNewName,
        label = { Text(stringResource(R.string.productName)) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.spacing_normal)))
}

@Composable
private fun ProductPrice(productEdited: Boolean, price: Long, onNewPrice: (String) -> Unit) {
    OutlinedTextField(
        value = price.toString(),
        onValueChange = onNewPrice,
        label = { Text(stringResource(R.string.productPrice)) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.spacing_normal)))
}

@Composable
private fun ProductStock(productEdited: Boolean, stock: Int, onNewStock: (String) -> Unit) {
    OutlinedTextField(
        value = stock.toString(),
        onValueChange = onNewStock,
        label = { Text(stringResource(R.string.productStock)) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.spacing_normal)))
}

@Composable
private fun ProductDescription(productEdited: Boolean, description: String, onNewDescription: (String) -> Unit) {
    OutlinedTextField(
        value = description,
        onValueChange = onNewDescription,
        label = { Text(stringResource(R.string.productDescr)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Default),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.spacing_normal)))
}

@Composable
private fun SellBottle(onSellBottle: () -> Unit){
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(R.dimen.spacing_normal))
            .padding(horizontal = dimensionResource(R.dimen.spacing_normal)),
        onClick = onSellBottle) {
        Text(text = stringResource(id = R.string.product_sell))
    }
}

@Composable
private fun SaveChanges(onSaveChanges: () -> Unit){
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(R.dimen.spacing_normal))
            .padding(horizontal = dimensionResource(R.dimen.spacing_normal)),
        onClick = onSaveChanges) {
        Text(text = stringResource(id = R.string.product_save))
    }
}