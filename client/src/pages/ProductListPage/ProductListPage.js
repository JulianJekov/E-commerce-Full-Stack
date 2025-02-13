import React, { useEffect, useMemo, useState } from 'react'
import FilterIcon from '../../components/common/FilterIcon'
import content from '../../data/content.json'
import Categories from '../../components/Filters/Categories'
import PriceFilter from '../../components/Filters/PriceFilter'
import ColorsFilter from '../../components/Filters/ColorsFilter'
import SizeFilter from '../../components/Filters/SizeFilter'
import ProductCard from './ProductCard'
import { getAllProducts } from '../../api/fetchProducts'
import { useDispatch, useSelector } from 'react-redux'
import { setLoading } from '../../store/features/common'

const categories = content?.categories;

const ProductListPage = ({ categoryType }) => {

    const categoryData = useSelector((state)=> state?.categoryState?.categories)
    const dispatch = useDispatch();
    const [products, setProducts] = useState([])

    const categoryContent = useMemo(() => {
        return categories?.find((category) => category.code === categoryType)
    }, [categoryType])

    const category = useMemo(()=> {
        return categoryData?.find(element => element?.code === categoryType)
    }, [categoryData, categoryType])

    useEffect(()=> {
        dispatch(setLoading(true))
        getAllProducts(category?.id).then(res=>{
            setProducts(res)
        }).catch(err=>{

        }).finally(()=>{
            dispatch(setLoading(false))
        })
    }, [category?.id, dispatch])

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
                    <p className='text-black text-lg'>{category?.description}</p>
                    <div className='pt-4 grid grid-cols-1 lg:grid-cols-3 md:grid-cols-2 gap-8 px-2'>
                        {products?.map((item, index) => 
                            <ProductCard key={item?.id+"_"+index} {...item} title={item?.name}/>
                        )}
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ProductListPage