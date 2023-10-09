// src/components/ProductTypeFilter.js

import React from 'react';



const ProductTypeFilter = ({ productType, onClick }) => {

    return (

        <span className= "productTypeFilter" onClick={() => onClick (productType)}>

            {productType}

    </span>

    );

};



export default ProductTypeFilter;
