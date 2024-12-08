import React from 'react'
import { colorSelector } from '../../components/Filters/ColorsFilter'

const ProductColors = ({colors}) => {
  return (
    <div className='flex'>
        {colors?.map((color,index)=> (
            <div className={`rounded-[50%] w-4 h-4 mr-2`} style={{background:colorSelector[color]}}> </div>
        ))}
    </div>
  )
}

export default ProductColors