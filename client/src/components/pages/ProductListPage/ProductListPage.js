import React, { useMemo } from 'react'
import FilterIcon from '../../common/FilterIcon'
import content from '../../../data/content.json'
import Categories from '../../Filters/Categories'
import PriceFilter from '../../Filters/PriceFilter'
import ColorsFilter from '../../Filters/ColorsFilter'
import SizeFilter from '../../Filters/SizeFilter'
import ProductCard from './ProductCard'

const categories = content?.categories;
const products = content?.products;

const ProductListPage = ({ categoryType }) => {

    const categoryContent = useMemo(() => {
        return categories?.find((category) => category.code === categoryType)
    }, [categoryType])

    const productListItems = useMemo(() => {
        return content?.products?.filter((product) => product?.category_id === categoryContent?.id)
    }, [categoryContent])

    return (
        <div>
            <div className='flex'>

                {/* Filters */}
                <div className='w-[20%] p-[20px] m-[20px] border rounded-lg'>
                    <div className='flex justify-between'>
                        <p className='text-[16px] text-gray-600'>Filter</p>
                        <FilterIcon />
                    </div>
                    {/* Product types */}
                    <div>
                        <p className='text-[16px] text-black mt-5'>Categories</p>
                        <Categories types={categoryContent?.types} />
                        <hr></hr>
                    </div>

                    {/* Price */}
                    <PriceFilter />
                    <hr></hr>

                    {/* Colors */}
                    <ColorsFilter colors={categoryContent?.meta_data?.colors}/>
                    <hr></hr>

                    {/* Sizee */}
                    <SizeFilter size={categoryContent?.meta_data?.sizes}/>

                </div>

                {/* Products */}
                <div className='p-[15px]'>
                    <p className='text-black text-lg'>{categoryContent?.description}</p>
                    <div className='pt-4 grid grid-cols-1 lg:grid-cols-3 md:grid-cols-2 gap-8 px-2'>
                        {productListItems?.map((item, index) => 
                            <ProductCard key={index} {...item} />
                        )}
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ProductListPage