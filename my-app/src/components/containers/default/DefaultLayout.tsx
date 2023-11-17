import { Outlet } from "react-router-dom";
import DefaultHeader from "./DefaultHeader.tsx";

const DefaultLayout = () => {
    return (
        <>
            <DefaultHeader />
            <div className="container mx-auto px-4 ">
                {/* Сюди підставляється компонет один із групи комеонетів, які відносяться до даного Layout */}
                <Outlet/>
            </div>
        </>
    );
};

export default DefaultLayout;