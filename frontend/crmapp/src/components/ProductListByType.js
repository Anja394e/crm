// src/components/ProductListByType.js

import React from 'react';
import './style.css';


const ProductListByType = ({products, selectedProductType}) => {

    return (

        <div>

            <h3>Products of Type: {selectedProductType}</h3>

            <ul>

                {products

                    .filter((product) => product.productType === selectedProductType)

                    .map((product) => (

                        <li key={product.id}>

                            <strong>Product Name:</strong> {product.productName}

                        </li>

                    ))}

            </ul>

        </div>

    );

};


export default ProductListByType;